package com.bdocg.service;

import com.bdocg.domain.Card;
import com.bdocg.domain.Deck;
import com.bdocg.domain.Game;
import com.bdocg.domain.Player;
import com.bdocg.repository.IGameRepository;
import com.bdocg.view.CardCountView;
import com.bdocg.view.CardSuitCountView;
import com.bdocg.view.PlayerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import static java.util.stream.Collectors.groupingBy;

@Service
public class GameService implements IGameService {

    private IGameRepository gameRepository;

    private IDeckService deckService;

    private IPlayerService playerService;

    @Autowired
    public GameService(IGameRepository gameRepository, IDeckService deckService, IPlayerService playerService) {
        this.gameRepository = gameRepository;
        this.deckService = deckService;
        this.playerService = playerService;
    }

    @Override
    public void createGame(String name) {
        gameRepository.save(new Game(name));
    }

    @Override
    public void deleteGame(String name) {
        gameRepository.findGameByName(name).ifPresent(game -> gameRepository.delete(game));
    }

    @Override
    public void addDeckToGame(String gameName, String deckName) {
        Game game = findGameByName(gameName);
        Deck deck = deckService.getDeck(deckName);
        game.addDeck(deck);
        gameRepository.save(game);
    }

    private Game findGameByName(String gameName) {
        return gameRepository.findGameByName(gameName)
                .orElseThrow(() -> new NotFoundException("Game is not found by the specified name"));
    }

    @Override
    public void addPlayerToGame(String gameName, String playerName) {
        Game game = findGameByName(gameName);
        game.addPlayer(playerService.getPlayer(playerName));
        gameRepository.save(game);
    }

    @Override
    public void removePlayerFromGame(String gameName, String playerName) {
        Game game = findGameByName(gameName);
        List<Player> players = game.getPlayers();
        players.removeIf(player -> player.getName().equals(playerName));
        game.setPlayers(players);
        gameRepository.save(game);
    }

    @Override
    public void dealCardsToPlayer(String gameName, String playerName, int numberOfCards) {
        Game game = findGameByName(gameName);
        Player player = getPlayerFromGame(playerName, game);

        List<Card> shoe = game.getShoe();
        if (shoe.size() >= numberOfCards) {
            for (int i = 0; i < numberOfCards; i++) {
                dealCard(game, player, shoe);
            }
            gameRepository.save(game);
        }
    }

    private void dealCard(Game game, Player player, List<Card> shoe) {
        player.addCard(shoe.remove(0));
        game.setShoe(shoe);
    }

    private Player getPlayerFromGame(String playerName, Game game) {
        return game.getPlayers()
                    .stream()
                    .filter(player -> player.getName().equals(playerName))
                    .findAny()
                    .orElseThrow(() -> new NotFoundException("Player is not found by the specified name"));
    }

    @Override
    public List<PlayerView> getPlayersInGame(String gameName) {
        Game game = findGameByName(gameName);
        List<Player> players = game.getPlayers();
        List<PlayerView> playerViews = convertToPlayerView(players);
        playerViews.sort(Comparator.comparingInt(PlayerView::getTotalValueOfCards).reversed());
        return playerViews;
    }

    private List<PlayerView> convertToPlayerView(List<Player> players) {
        List<PlayerView> playerViews = new ArrayList<>();
        players.forEach(player -> {
            PlayerView playerView = new PlayerView();
            playerView.setName(player.getName());
            playerView.setTotalValueOfCards(player.getCards()
                    .stream()
                    .mapToInt(Card::getScore)
                    .sum());
            playerViews.add(playerView);
        });
        return playerViews;
    }

    @Override
    public Set<CardSuitCountView> getCountOfUndealtCardsPerSuit(String gameName) {
        Map<String, List<Card>> suitGroups = findGameByName(gameName).getShoe().stream()
                .collect(groupingBy(Card::getSuit));

        return convertToCardSuitCountViews(suitGroups);
    }

    private Set<CardSuitCountView> convertToCardSuitCountViews(Map<String, List<Card>> suitGroups) {
        Set<CardSuitCountView> cardSuitCountViews = new HashSet<>();
        suitGroups.forEach((suit, cards) -> {
            CardSuitCountView cardSuitCountView = new CardSuitCountView();
            cardSuitCountView.setSuit(suit);
            cardSuitCountView.setCount(cards.size());
            cardSuitCountViews.add(cardSuitCountView);
        });
        return cardSuitCountViews;
    }

    @Override
    public List<CardCountView> getCountOfUndealtCards(String gameName) {
        TreeMap<String, Map<Integer, List<Card>>> suitScoreGroups = findGameByName(gameName).getShoe().stream()
                .collect(groupingBy(Card::getSuit, TreeMap::new, groupingBy(Card::getScore)));

        return convertToCardCountViews(suitScoreGroups);
    }

    private List<CardCountView> convertToCardCountViews(TreeMap<String, Map<Integer, List<Card>>> suitScoreGroups) {
        List<CardCountView> cardCountViews = new ArrayList<>();
        suitScoreGroups.forEach((suit, scoreGroup) -> {
            reverseValueGroupOrder(scoreGroup).forEach((cardValue, cards) -> {
                cardCountViews.add(convertToCardCountView(suit, cards));
            });
        });
        return cardCountViews;
    }

    private NavigableMap<Integer, List<Card>> reverseValueGroupOrder(Map<Integer, List<Card>> valueGroup) {
        return new TreeMap<>(valueGroup).descendingMap();
    }

    private CardCountView convertToCardCountView(String cardSuit, List<Card> cards) {
        CardCountView cardCountView = new CardCountView();
        cardCountView.setSuit(cardSuit);
        cardCountView.setValue(cards.get(0).getValue());
        cardCountView.setCount(cards.size());
        return cardCountView;
    }

    @Override
    public void shuffleShoe(String gameName) {
        Game game = findGameByName(gameName);
        List<Card> shoe = game.getShoe();
        List<Card> shuffledShoe = new ArrayList<>(shoe.size());

        Random random = new Random();
        while (!shoe.isEmpty()) {
            shuffledShoe.add(shoe.remove(random.nextInt(shoe.size())));
        }

        game.setShoe(shuffledShoe);
        gameRepository.save(game);
    }
}
