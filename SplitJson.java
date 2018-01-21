import java.io.IOException;
import java.util.Stack;

/**
 * Created by Malcolm.Li on 1/4/2018.
 */
public class SplitJson {

    public static void process(String str) throws IOException {
        String temp = "";
        String newStr = "";
        String lastCaseBeforeColon = "";
        String nextCaseAfterColon = "";
        StringBuffer stringBuffer = new StringBuffer(str);
        boolean isQuote = false; // "
        boolean isColon = false; // :
        boolean isComma = false; // ,
        boolean isBracket = false; // ,
        int index = 0;
        int quoteCount = 0;
        String currentCase = "";
        for (int i = 1; i <= stringBuffer.length(); i++) {
            temp = stringBuffer.substring(i-1, i);
            if (temp.equals("{") || temp.equals("[")) {
                newStr += temp;
                index = i;
            } else if (temp.equals("\'") || temp.equals("\"")) {
                quoteCount++;
            } else if (temp.equals(":")) {
                currentCase = temp;
                temp =  stringBuffer.substring(index, i).trim();
                if (nextCaseAfterColon.equals("")) {
                    nextCaseAfterColon = stringBuffer.substring(i, i + 1);
                }
                if (quoteCount == 0) {
                    temp =  stringBuffer.substring(index, i-1).trim();
                    newStr += "\"" + temp + "\"" + currentCase;
                } else if (quoteCount == 2) {
                    newStr += temp;
                    quoteCount = 0;
                } else {
                    newStr += temp;
                }
                index = i;
                isColon = true;
            } else if (temp.equals(",")) {
                currentCase = temp;
                temp =  stringBuffer.substring(index, i);
                if (lastCaseBeforeColon.equals("")) {
                    lastCaseBeforeColon = stringBuffer.substring(i - 2, i - 1);
                }
                if ((quoteCount == 0 && !isBracket) || (!nextCaseAfterColon.equals("\"") || !lastCaseBeforeColon.equals("\""))) {
                    temp =  stringBuffer.substring(index, i-1).trim();
                    newStr += "\"" + temp + "\"" + currentCase;
                    nextCaseAfterColon = "";
                    lastCaseBeforeColon = "";
                    quoteCount = 0;
                } else if (quoteCount == 2 || (nextCaseAfterColon.equals("\"") && lastCaseBeforeColon.equals("\""))) {
                    newStr += temp;
                    quoteCount = 0;
                    nextCaseAfterColon = "";
                    lastCaseBeforeColon = "";
                } else {
                    newStr += temp;
                }
                index = i;
                isBracket = false;
            } else if (temp.equals("}") || temp.equals("]")) {
                currentCase = temp;
                temp =  stringBuffer.substring(index, i);
                if (quoteCount == 0 && index != i - 1) {
                    temp =  stringBuffer.substring(index, i-1).trim();
                    newStr += "\"" + temp + "\"" + currentCase;
                } else if (quoteCount == 2) {
                    newStr += temp;
                    quoteCount = 0;
                } else {
                    newStr += temp;
                }
                index = i;
                isBracket = true;
            }
            //System.out.println(newStr);
        }
        System.out.println(newStr);
    }

    public static void main(String[] args) throws IOException {
        String str  = "{\"meiaId\":1,\"duration\":11,1:\"2:9\"1,status:\"2\",flavors:[{extension:mp4,videoBitrate:\"437\",height:\"240\",flavorAssetId:1_wrj6ir5v,width:\"320\",flavorParamsName:SD/Small - WEB/MBL (H264/900),url:\"https://lkpm130835-a.akamaihd.net/p/1930471/sp/193047100/serveFlavor/entryId/1_znj3v45h/v/1/ev/3/flavorId/1_wrj6ir5v/forceproxy/true/name/a.mp4\",audioBitrate:\"64\"},{extension:mp4,videoBitrate:\"440\",height:\"240\",flavorAssetId:1_tseyjs99,width:\"320\",flavorParamsName:Basic/Small - WEB/MBL (H264/400),url:\"https://lkpm130835-a.akamaihd.net/p/1930471/sp/193047100/serveFlavor/entryId/1_znj3v45h/v/1/ev/3/flavorId/1_tseyjs99/forceproxy/true/name/a.mp4\",audioBitrate:\"64\"}],externalId:1_znj3v45h,mediaType:Kaltura}";
        process(str);
    }
}