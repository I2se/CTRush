package fr.lyrania.ctrush.managers.files;

import fr.lyrania.ctrush.Main;

import java.io.*;

public class FileManager {

    public static void saveResourceAs(String resourcePath, String outputPath) {
        if(resourcePath == null || resourcePath.isEmpty()) {
            throw new IllegalArgumentException("Le fichier de config est incorrect");
        }

        InputStream in = Main.getInstance().getResource(resourcePath);
        if(in == null) {
            throw new IllegalArgumentException("Le fichier de config est inexistant");
        }

        if(!Main.getInstance().getDataFolder().exists() && !Main.getInstance().getDataFolder().mkdir()) {
            Main.getInstance().getLogger().severe("Le dossier du plugin n'a pas pu être creer !");
        }

        File outFile = new File(Main.getInstance().getDataFolder(), outputPath);

        try {
            if(!outFile.exists()) {
                Main.getInstance().getLogger().info("Le fichier de config n'a pas était trouvé - Creation en cours");

                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int n;

                while((n = in.read(buf)) >= 0) {
                    out.write(buf, 0, n);
                }

                out.close();
                in.close();

                if(!outFile.exists()) {
                    Main.getInstance().getLogger().severe("Le fichier de config a echoué durant la creation");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
