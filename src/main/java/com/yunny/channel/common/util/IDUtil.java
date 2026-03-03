package com.yunny.channel.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @version 0.0.1
 * @date Jul 24, 2018 7:04:33 PM
 */
public class IDUtil {

    static Logger LOG = LoggerFactory.getLogger(IDUtil.class);

    static final int TokenRandomSize = 24;

    static final int IDRandomSize = 30;

    public static final String genToken(long usrId) {
        return genToken(usrId, "", "");
    }

    /**
     * 生成Api登录的token: 0xUsrID+24位随机数
     * <p>
     * openID sessionKey可选参数，可以是空字符串
     *
     * @return
     */
    public static final String genToken(long usrId, String openID, String sessionKey) {
        String hexUsr = Long.toHexString(usrId);

        String hexMS = Long.toHexString(System.currentTimeMillis());
        String hexID = StringUtil.isEmpty(openID) ? Integer.toHexString(new Date().hashCode())
                : Integer.toHexString(sessionKey.hashCode());
        String hexKey = Integer.toHexString(sessionKey.hashCode());

        int ranlen = TokenRandomSize - hexID.length() - hexKey.length()-hexMS.length();

        StringBuilder buf = new StringBuilder(32);
        buf.append(hexMS);
        buf.append(hexID);
        buf.append(hexKey);
        for (int i = 0; i < ranlen; i++) {
            buf.append(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 16)));
        }
        String ranStr = buf.toString();
        if (ranlen < 0) ranStr = ranStr.substring(0, TokenRandomSize);
        return hexUsr + ranStr;
    }

    public static final String genEmailToken(long usrId) {
        return genToken(usrId);
    }

    public static final long decodeUserIDFromEmailToken(String token) {
        return decodeUserIDFromToken(token);
    }

    public static final long decodeUserIDFromToken(String token) {
        if (token == null || token.length() <= TokenRandomSize) return -1;
        String hexUsr = token.substring(0, token.length() - TokenRandomSize);
        return Long.parseLong(hexUsr, 16);
    }

    /**
     * <pre>
     * 将imgCodeId转换为user_imgcode的编号ID,
     * 保证imgCodeId在1小时内具有相同的innerImgCodeId
     * </pre>
     *
     * @param imgCodeId
     * @return
     */
    public static final int innerImgCodeId(String imgCodeId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        // todo ApiConst.IMGCODE_MAX_ID =10000 暂定一万
        return Math.abs((imgCodeId + sdf.format(new Date())).hashCode() % 10000) + 1;
        // LOG.info("innerImgCode {} to {}", imgCodeId, r);
    }

    /**
     * @param apiVersion
     * @return
     */
    public static final String genImgCodeId(String apiVersion) {
        if (apiVersion == null) {
            apiVersion = "";
        }

        String hexMS = Long.toHexString(System.currentTimeMillis());
        String hexVersion = Integer.toHexString(apiVersion.hashCode());

        int ranlen = IDRandomSize - hexMS.length() - hexVersion.length();

        StringBuilder buf = new StringBuilder(32);
        buf.append(hexMS);
        buf.append(hexVersion);
        for (int i = 0; i < ranlen; ++i) {
            buf.append(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 16)));
        }
        String ranStr = buf.toString();
        if (ranlen < 0) ranStr = ranStr.substring(0, IDRandomSize);
        return ranStr;
    }

    public static final String genMobileVcode(int size) {
        if (size < 1) {
            size = 4;
        }
        StringBuilder buf = new StringBuilder(size);
        for (int i = 0; i < size; ++i) {
            buf.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return buf.toString();
    }

    /**
     * max(userId) = Long.MAX_VALUE
     *
     * @param userId
     * @return versionHex[1] + userIdHexLengthHex[1] + userIdHex[] + RandomHex, max lenght 24
     */
    public static final String genApikeyV1(long userId) {
        int maxLen = 24;
        String versionHex = "1";//1-f

        String userIdHex = Long.toHexString(userId); // max length 1-16
        String userIdHexLengthHex = Integer.toHexString(userIdHex.length() - 1);

        StringBuffer buf = new StringBuffer(24);
        buf.append(versionHex);
        buf.append(userIdHexLengthHex);
        buf.append(userIdHex);
        // RandomHex
        int ranlen = maxLen - 2 - userIdHex.length();
        for (int i = 0; i < ranlen; ++i) {
            buf.append(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 16)));
        }

        return buf.toString();
    }

    public static final long readUserIdFromApikey(String apikey) {
        if (StringUtil.isEmpty(apikey))
            return 0L;

        char version = apikey.charAt(0);
        try {
            if ('1' == version) {
                int userIdHexLength = Integer.parseInt(apikey.substring(1, 2), 16) + 1;
                if (userIdHexLength > 0 && userIdHexLength <= 16) {
                    String userIdHex = apikey.substring(2, 2 + userIdHexLength);
                    return Long.parseLong(userIdHex, 16);
                }

            }
            //TODO else
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return 0L;
    }

    public static final String genHttpProxyId(int serverId, long userId) {
        int maxLen = 32;

        String idHex = Integer.toHexString(serverId); // max length 1-16
        String idHexLen = Integer.toHexString(idHex.length() - 1);
        String userHex = Long.toHexString(userId);
        String userHexLen = Integer.toHexString(userHex.length() - 1);
        String tsHex = Integer.toHexString(Long.toHexString(System.currentTimeMillis()).hashCode());

        StringBuffer buf = new StringBuffer(maxLen);
        buf.append(idHexLen);
        buf.append(idHex);
        buf.append(userHexLen);
        buf.append(userHex);
        buf.append(tsHex);
        // RandomHex
        int ranlen = maxLen - 2 - idHex.length() - userHex.length() - tsHex.length();
        for (int i = 0; i < ranlen; ++i) {
            buf.append(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 16)));
        }

        return buf.toString();
    }

    public static final long readUserIdFromHttpProxyId(String httpProxyId) {
        if (StringUtil.isEmpty(httpProxyId))
            return 0L;

        try {
            int IdHexLength = Integer.parseInt(httpProxyId.substring(0, 1), 16) + 1;
            if (IdHexLength >= 0) {
                int idUserLen = Integer.parseInt(httpProxyId.substring(IdHexLength + 1, IdHexLength + 2), 16) + 1;
                if (idUserLen > 0 && IdHexLength <= 16) {
                    String userHex = httpProxyId.substring(IdHexLength + 2, IdHexLength + 2 + idUserLen);
                    return Long.parseLong(userHex, 16);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return 0L;
    }

    public static final int readServerIdFromHttpProxyId(String httpProxyId) {
        if (StringUtil.isEmpty(httpProxyId))
            return 0;

        try {
            int IdHexLength = Integer.parseInt(httpProxyId.substring(0, 1), 16) + 1;
            if (IdHexLength > 0 && IdHexLength <= 16) {
                String IdHex = httpProxyId.substring(1, 1 + IdHexLength);
                return Integer.parseInt(IdHex, 16);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return 0;
    }

    public static final String genUserInviteCode() {
        return "";
    }

    // private static final char inviteCode[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


    /**
     * @param userId
     * @param name   app name
     * @return
     */
    public static final String genAppId(long userId, String name) {
        int maxLen = 32;
        String versionHex = "1";

        String userIdHex = Long.toHexString(userId); // max length 16
        String userIdHexLengthHex = Integer.toHexString(userIdHex.length() - 1);
        String nameHex = name == null ? "" : Integer.toHexString(name.hashCode());

        StringBuffer buf = new StringBuffer(maxLen);
        buf.append(versionHex);
        buf.append(userIdHexLengthHex);  //1
        buf.append(userIdHex);
        buf.append(nameHex);

        //RandomHex
        int ranlen = maxLen - 2 - userIdHex.length() - nameHex.length();
        for (int i = 0; i < ranlen; ++i) {
            buf.append(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 16)));
        }

        return buf.toString();
    }

    public static final long readUserIDFromAppId(String appId) {
        if (StringUtil.isEmpty(appId))
            return 0L;

        char version = appId.charAt(0);
        try {
            if ('1' == version) {
                int userIdHexLength = Integer.parseInt(appId.substring(1, 2), 16) + 1;
                if (userIdHexLength > 0 && userIdHexLength <= 16) {
                    String userIdHex = appId.substring(2, 2 + userIdHexLength);
                    return Long.parseLong(userIdHex, 16);
                }

            }
            //TODO error
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return 0L;
    }

    public static final String genNodeToken(String nodeId) {
        int maxLen = 24;

        String nameHex = Integer.toHexString(nodeId.hashCode());
        String txHex = Integer.toHexString(new Date().hashCode());

        StringBuffer buf = new StringBuffer(maxLen);
        buf.append(nameHex);
        buf.append(txHex);

        //RandomHex
        int ranlen = maxLen - nameHex.length() - txHex.length();
        for (int i = 0; i < ranlen; ++i) {
            buf.append(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 16)));
        }

        return buf.toString();
    }

    public static final boolean isInvalidToken(String token, String nodeId) {
        String nameHex = Integer.toHexString(nodeId.hashCode());
        return !token.startsWith(nameHex);
    }

    public static final String genInsId(String nodeId, int ins) {
        int maxLen = 32;

        String insHex = Integer.toHexString(ins);
        String insHexLen = Integer.toHexString(insHex.length() - 1);
        String nodeIdHex = Integer.toHexString(nodeId.hashCode());
        String tsHex = Integer.toHexString(new Date().hashCode());

        StringBuffer buf = new StringBuffer(maxLen);
        buf.append(insHexLen);  //1
        buf.append(insHex); // max 8
        buf.append(nodeIdHex); // max 8
        buf.append(tsHex); //max 8

        //RandomHex
        int ranlen = maxLen - insHexLen.length() - insHex.length() - nodeIdHex.length() - tsHex.length();
        for (int i = 0; i < ranlen; ++i) {
            buf.append(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 16)));
        }

        return buf.toString();
    }

    public static final String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    /**
     * 生成邀请码
     *
     * @return
     */
    public static String genNodeInviteCode() {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            buf.append((char) ((ThreadLocalRandom.current().nextInt(1, 26) % 26 + 97) & 0xffff));
        }
        return buf.toString().toUpperCase();
    }

}
