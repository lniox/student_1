package security;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
//MD5:https://blog.csdn.net/learning__java/article/details/90377677?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522165138633416781683936767%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=165138633416781683936767&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~rank_v31_ecpm-2-90377677.142^v9^pc_search_result_cache,157^v4^control&utm_term=MD5%E6%98%AF%E4%BB%80%E4%B9%88&spm=1018.2226.3001.4187
public final class MD5 {
    public static String encode(String origin) {
        try {
            final var md = MessageDigest.getInstance("MD5");
            final byte[] digest = md.digest(origin.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String encode(char[] origin) {
        return encode(String.valueOf(origin).intern());
    }
}
