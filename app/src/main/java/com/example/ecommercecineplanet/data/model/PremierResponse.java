package com.example.ecommercecineplanet.data.model;

import java.util.List;

public class PremierResponse {

    private List<Premiere> premieres;

    public List<Premiere> getPremieres() {
        return premieres;
    }

    public static class Premiere {
        private String description;
        private String image;

        public String getDescription() {
            return description;
        }


        public String getImage() {
            return image;
        }

    }
}
