package com.game.mancala.models;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString

/**
 * A class to represent the player playing a certain game.
 * Found as a part of {@link Game}
 */
public class Player {
    private String name;
    private PlayerType playerType;
}