package net.guardiandev.dataclassesgenerator;

import com.google.gson.Gson;
import net.jay.terrariawikiparser.data.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static final Gson gson = new Gson();
    public static final String itemsUrl = "https://raw.githubusercontent.com/jaydevelopsstuff/TerrariaWikiParser/master/cache/items.json";

    public static void main(String[] args) throws IOException, URISyntaxException {
        Document itemsDoc = Jsoup.connect(itemsUrl).get();
        String itemsJson = itemsDoc.body().text();

        String itemsEnumTemplate = Files.readString(Paths.get(Main.class.getClassLoader().getResource("ItemsEnumTemplate.java").toURI()));

        Item[] items = gson.fromJson(itemsJson, Item[].class);
        StringBuilder enumsBuilder = new StringBuilder();
        int index = 0;
        for(Item item : items) {
            String enumS = "    " + item.getInternalName() + "(" + item.getId() + ", \"" + item.getName() + "\", \"" + item.getInternalName()
                    + "\", \"" + item.getType() + "\", " + item.getRarity() + ", " + item.getBlockId() + ", " + item.getBlockSubId() + (index != items.length - 1 ? "),\n" : ")");

            enumsBuilder.append(enumS);
            index++;
        }
        StringBuilder itemsEnumBuilder = new StringBuilder(itemsEnumTemplate);
        itemsEnumBuilder.insert(51, enumsBuilder);
        String itemsEnum = itemsEnumBuilder.toString();

        Files.writeString(Paths.get("./Items.java"), itemsEnum);
    }
}
