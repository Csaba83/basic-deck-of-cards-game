package com.bdocg.service;

import com.bdocg.domain.Card;
import com.bdocg.domain.Player;
import com.bdocg.repository.PlayerRepository;
import com.bdocg.view.CardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService implements IPlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void createPlayer(String name) {
        playerRepository.save(new Player(name));
    }

    @Override
    public Player getPlayer(String name) {
        return findPlayerByName(name);
    }

    private Player findPlayerByName(String name) {
        Optional<Player> optionalPlayer = playerRepository.findPlayerByName(name);
        return optionalPlayer.orElseThrow(() -> new NotFoundException("Player is not found by the specified name"));
    }

    @Override
    public List<CardView> getPlayerCards(String name) {
        return convertToCardViews(findPlayerByName(name).getCards());
    }

    private List<CardView> convertToCardViews(List<Card> cards) {
        List<CardView> cardCountViews = new ArrayList<>();
        for (Card card : cards) {
            CardView cardView = new CardView();
            cardView.setSuit(card.getSuit());
            cardView.setValue(card.getValue());
            cardCountViews.add(cardView);
        }
        return cardCountViews;
    }
}
