package com.bdn.ozbe;

    public class User {

        private final String raumID;
        private String stuhle;
        private String tische;
        private String austattung;
        private String mangel;
        private int imageId;

        public User(String raumID,  String stuhle, String tische, String austattung, String mangel, int imageId) {
            this.raumID = raumID;
            this.stuhle = stuhle;
            this.tische = tische;
            this.mangel = mangel;
            this.austattung = austattung;
            this.imageId = imageId;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public User() {
            this.raumID = "AAAA";
            this.stuhle = "200";
            this.tische = "100";
            this.mangel = "mangel";
            this.austattung = "austattung";
            this.imageId = R.drawable.h;
        }

        public String getRaumID() {
            return raumID;
        }

        public String getStuhle() {
            return stuhle;
        }

        public void setStuhle(String stuhle) {
            this.stuhle = stuhle;
        }

        public String getTische() {
            return tische;
        }

        public void setTische(String tische) {
            this.tische = tische;
        }

        public String getAustattung() {
            return austattung;
        }

        public void setAustattung(String austattung) {
            this.austattung = austattung;
        }

        public String getMangel() {
            return mangel;
        }

        public void setMangel(String mangel) {
            this.mangel = mangel;
        }
    }