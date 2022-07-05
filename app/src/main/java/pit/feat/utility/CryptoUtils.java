package pit.feat.utility;

/**
 * Created by Usuário on 26/05/2016.
 */
public class CryptoUtils {


    public CryptoUtils(){

    }



    public static byte[] computeHash(String x)
            throws Exception
    {
        java.security.MessageDigest d =null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return  d.digest();
    }

    public static String byteArrayToHexString(byte[] b){
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++){
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }


    public String generateHash(String password){
        String hash = null;
        try {
            hash = byteArrayToHexString(CryptoUtils.computeHash(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

}