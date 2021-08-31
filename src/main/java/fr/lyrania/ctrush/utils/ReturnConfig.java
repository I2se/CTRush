package fr.lyrania.ctrush.utils;

public class ReturnConfig {

    public static int getConfigTeam(int nbrplayers) {
        if(nbrplayers == 4) {
            return 2;
        } else if(nbrplayers == 5 || nbrplayers == 6) {
            return 3;
        } else if(nbrplayers == 7 || nbrplayers == 8) {
            return 4;
        } else if(nbrplayers > 8 && nbrplayers <= 12 ) {
            return 5;
        } else if(nbrplayers > 12 ) {
            return 6;
        }
        return 1;
    }
}
