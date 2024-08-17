package com.example.ecommercecineplanet.data.model;

import java.util.List;

public class CandyStoreResponse {

    private List<Items> items;

    public List<Items> getItems() {
        return items;
    }
    public static class Items {
        private String description;

        public void setImageIndex(int imageIndex) {
            this.imageIndex = imageIndex;
        }

        private int imageIndex;
        private String price;
        private String name;
        private int quantity;
        public String getDescription() {
            return description;
        }


        public String getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getImageIndex() {
            return imageIndex;
        }




    }

}
