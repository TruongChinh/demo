package application.common;

import application.model.DataApiResult;
import org.springframework.http.HttpStatus;

public class Common {
    public static DataApiResult setResult(DataApiResult rs, int http, String mess, Object data) {
        rs.setData(data);
        rs.setMessage(mess);
        rs.setHttp(http);
        return rs;
    }

    public static DataApiResult setResult(DataApiResult rs, int http, Object data) {
        if (http == HttpStatus.OK.value()) {
            setResult(rs, http, "success", data);
        } else if (http == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            setResult(rs, http, "failed", null);
        } else if (http == HttpStatus.NO_CONTENT.value()) {
            setResult(rs, http, "success", null);
        }
        return rs;
    }

    public static String trimStringOfNaughtyCharacters(String strToBeTrimmed) {
        return strToBeTrimmed.replace("\\x00", "\n")           // remove all NULLs
                .replace("\\x0d", "\n")   // CRLF to spac
                .replace("\\x0a", "\n")
                .replace("\\x3c", "&lt;")
                .replace("\\x3e", "&quot;")
                .replace("\u001B[H","")
                .replace("\u001B[J","")
                .replace("\u001B[1;37m","")
                .replace("\u001B[1;33m","")
                .replace("\u001B[0;36m","")
                .replace("\u001B[0;37m","")
                .replace("\u001B[1;30m","")
                .replace("\u001B[1;34m","")
                .replace("\u001B[1;35m","")
                .replace("\u001B[0m","")
                .replace("\u001B[1;32m","");

        // this always comes last
//        strToBeTrimmed.replaceAll("\\s{20}",null);          // remove whitespace in 20-char chunks

    }

//    public static void main(String[] args) {
//        String a = "data':'HTTP/1.1 200 OK\\x0aServer: nginx\\x0aDate: Sat, 22 Feb 2020 17:19:59 GMT\\x0aContent-Type: ";
//        System.out.println(a.replace("\\x0a","\n"));
//    }

}
