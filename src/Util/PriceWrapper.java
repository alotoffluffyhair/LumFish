package Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.powerbot.script.wrappers.Item;

/*
 * All credits to Coma
 * for this class
 * 
 * 
 */


public class PriceWrapper {
    private HashMap<Integer, Integer> priceMap = new HashMap<>();

    public PriceWrapper(final int... ids) {
        add(ids);
    }

    public void put(final int id, final int value) {
        priceMap.put(id, value);
    }

    public void add(final int... itemIds) {
        if (itemIds.length < 1) {
            return;
        }
        List<Integer> nonCachedIds = new ArrayList<>();
        for (int i : itemIds) {
            if (!priceMap.containsKey(i)) {
                nonCachedIds.add(i);
            }
        }
        priceMap.putAll(lookup(nonCachedIds.toArray(new Integer[nonCachedIds.size()])));
    }

    public void add(final Item[] items) {
        final int[] ids = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            ids[i] = items[i].getId();
        }
        add(ids);
    }

    public int getPrice(final int id) {
        if (!priceMap.containsKey(id)) {
            add(id);
        }
        return priceMap.get(id);
    }

    private HashMap<Integer, Integer> lookup(final Integer... ids) {
        HashMap<Integer, Integer> map = new HashMap<>();
        if (ids.length < 1) {
            return map;
        }
        for (int i : ids) {
            map.put(i, lookup(i));
        }

        return map;
    }

    private int lookup(final int id) {
        try {
            final URL add = new URL("http://www.tip.it/runescape/json/ge_single_item?item=" + id);
            final URLConnection con = add.openConnection();
            con.setRequestProperty("Connection", "close");
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0");

            final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            final String line = in.readLine();
            final String price = (line.substring(line.indexOf("mark_price") + 13,
                    line.indexOf(",\"daily_gp") - 1)).replaceAll("[\\D]", "");
            return Integer.parseInt(price);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}