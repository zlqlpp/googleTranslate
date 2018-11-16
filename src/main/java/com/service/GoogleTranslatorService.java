package com.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.util.Language;


public class GoogleTranslatorService   {
	
	public static void main(String[] args){
		try {
			System.out.println(new GoogleTranslatorService().translate("Architecture", Language.ENGLISH,Language.CHINESE));;
			System.out.println(new GoogleTranslatorService().translate("解释 ",Language.CHINESE ,Language.ENGLISH));;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    private static final String PATH = "https://translate.google.cn/translate_a/single";
    private static final NameValuePair[] ARG_LIST = {
            new BasicNameValuePair("client", "t"),
            new BasicNameValuePair("hl", toArgs(Language.CHINESE)),
            new BasicNameValuePair("dt", "at"),
            new BasicNameValuePair("dt", "bd"),
            new BasicNameValuePair("dt", "ex"),
            new BasicNameValuePair("dt", "ld"),
            new BasicNameValuePair("dt", "md"),
            new BasicNameValuePair("dt", "qca"),
            new BasicNameValuePair("dt", "rw"),
            new BasicNameValuePair("dt", "rm"),
            new BasicNameValuePair("dt", "ss"),
            new BasicNameValuePair("dt", "t"),
            new BasicNameValuePair("ie", "UTF-8"),
            new BasicNameValuePair("oe", "UTF-8")
    };


    private HttpClient client = HttpClients.createDefault();

    public String translate(String value, Language input, Language output) {
        HttpGet httpGet = new HttpGet(createURI(value.trim(), input, output));
        try {
				return client.execute(httpGet, response -> {
			    String data = EntityUtils.toString(response.getEntity());
			    int start = data.indexOf('\"') + 1;
			    int end = data.indexOf('\"', start);
			    return data.substring(start, end);
			});
		} catch ( Exception e) {
			e.printStackTrace();
		}
        return null;
    }


    private static URI createURI(String value, Language input, Language output) {
        try {
        	List l = new ArrayList();
        	l.add(new BasicNameValuePair("sl", toArgs(input)));
        	l.add(new BasicNameValuePair("tl", toArgs(output)));
        	l.add(new BasicNameValuePair("tk", getTk(value)));
        	l.add(new BasicNameValuePair("q", value));
        	
            return new URIBuilder()
                    .setPath(PATH)
                    .setParameters(ARG_LIST)
                    .addParameters(l).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    private static String getTk(String values) {
        final String KEY = "+-a^+6";
        final String LAST_KEY = "+-3^+b+-f";
        final long INIT_NUM = 406644L;
        final long REMAIN_NUM = (long) 1E6;
        final long DECIMAL_OR_NUM = 406644L;
        final long FIRST_OR_NUM = 3293161072L;
        long token = toNums(values.toCharArray()).stream()
                .reduce(INIT_NUM, (t, u) -> encode(t + u, KEY));
        token = encode(token, LAST_KEY) ^ FIRST_OR_NUM;
        token = token < 0 ? (token & Integer.MAX_VALUE) + Integer.MAX_VALUE : token;
        token %= REMAIN_NUM;
        return token + "." + (token ^ DECIMAL_OR_NUM);
    }

    private static List<Long> toNums(char[] values) {
        List<Long> valueList = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            long value = values[i];
            if (value <= Byte.MAX_VALUE) {
                valueList.add(value);
            } else if (value <= 2048) {
                valueList.add(value >> 6 | 192);
            } else {
                if (i + 1 < values.length
                        && (values[i + 1] & 64512) == 56320) {
                    value = 65536 + ((value & 1023) << 10) + (values[++i] & 1023);
                    valueList.add(value >> 18 | 240);
                    valueList.add(value >> 12 & 63 | 128);
                } else {
                    valueList.add(value >> 12 | 224);
                    valueList.add(value >> 6 & 63 | 128);
                }
                valueList.add(value & 63 | 128);
            }
        }
        return valueList;
    }

    private static long encode(long value, String key) {
        for (int i = 0; i < key.length() - 2; i += 3) {
            char char2 = key.charAt(i + 2);
            long status = char2 >= 'a' ? char2 - 87 : Long.valueOf(char2 + "");
            status = key.charAt(i + 1) == '+' ? value >>> status : value << status;
            value = key.charAt(i) == '+' ? value + status & 4294967295L : value ^ status;
        }
        return value;
    }


    private static String toArgs(Language language) {
        return language.toString() == "CHINESE" ? "zh-CN"
                : language.toString() == "ENGLISH" ? "en"
                : "";
    }
}