package com.revature.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private int petID;
    private String name;
    private String species;
    private String breed;
    private int ownerID;
}
