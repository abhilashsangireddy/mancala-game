package com.game.mancala.services;

import com.game.mancala.GameConstants;
import org.springframework.stereotype.Service;

/**
 * A service class to provide various initialization parameters to classes at appropriate
 * places. The added advantage of putting these values in a service rather than
 * providing static values directly is that this class facilitates a future necessity of implementing
 * an underlying logic to determine the value of various constants.
 *
 * Also, this way of initializing this class as a service helps in unit testing the code with
 * different possible constant value initializations.
 * {@link MancalaService} and MancalaServiceUnitTest demonstrate the above-mentioned capabilities.
 */

@Service
public class GameConstantProvider {

    public Integer getNumberOfSmallPitsPerPlayer(){
        return GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER;
    };

    public Integer getNumberOfStonesPerPit(){
        return GameConstants.NUMBER_OF_STONES_PER_PIT;
    }
}
