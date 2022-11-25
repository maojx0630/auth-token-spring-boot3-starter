package com.github.maojx0630.auth_token.util.rsa;

import com.github.maojx0630.auth_token.util.Base62;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * rsa工具类
 *
 * @author MaoJiaXing
 * @since 2020-12-07 14:18
 */
@SuppressWarnings("all")
public final class RsaUtils {

  /**
   * @return com.github.maojx0630.auth_token.util.rsa.RsaEntity
   * @author 毛家兴
   * @since 2022/10/26 09:12
   */
  public static RsaEntity createKeyPair() {
    KeyPairGenerator keyPairGenerator = null;
    try {
      keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    keyPairGenerator.initialize(1024);
    // 生成密钥对mvnc
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    // 获取公钥
    PublicKey publicKey = keyPair.getPublic();
    // 获取私钥
    PrivateKey privateKey = keyPair.getPrivate();
    return new RsaEntity(publicKey, privateKey);
  }

  /**
   * RSA加密
   *
   * @param str 要加密参数
   * @param publicKey 公钥
   * @return 密文
   */
  public static String encryptWithRSA(String str, String publicKey) {
    try {
      // 获取一个加密算法为RSA的加解密器对象cipher。
      Cipher cipher = Cipher.getInstance("RSA");
      // 设置为加密模式,并将公钥给cipher。
      cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
      // 获得密文
      byte[] secret = cipher.doFinal(str.getBytes());
      // 进行Base62编码
      return Base62.encode(secret);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * RSA解密
   *
   * @param secret 密文参数
   * @param privateKey 私钥
   * @return 解密后字符串
   */
  public static String decryptWithRSA(String secret, String privateKey) {
    try {
      Cipher cipher = Cipher.getInstance("RSA");
      // 传递私钥，设置为解密模式。
      cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
      // 解密器解密由Base62解码后的密文,获得明文字节数组
      byte[] b = cipher.doFinal(Base62.decode(secret));
      // 转换成字符串
      return new String(b);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取公钥对象
   *
   * @param key 公钥字符串
   * @return java.security.PublicKey
   * @author 毛家兴
   * @since 2022/10/26 09:13
   */
  public static PublicKey getPublicKey(String key) {
    try {
      byte[] keyBytes = toByte(key);
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return keyFactory.generatePublic(keySpec);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取私钥对象
   *
   * @param key 私钥
   * @return java.security.PrivateKey
   * @author 毛家兴
   * @since 2022/10/26 09:14
   */
  public static PrivateKey getPrivateKey(String key) {
    try {
      byte[] keyBytes = toByte(key);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return keyFactory.generatePrivate(keySpec);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * RSA签名
   *
   * @param content 待签内容
   * @param privateKey 私钥
   * @return 签名
   */
  public static String sign(String content, String privateKey) {
    try {
      // 用私钥对信息生成数字签名
      Signature stool = Signature.getInstance("MD5WithRSA");
      stool.initSign(getPrivateKey(privateKey));
      stool.update(content.getBytes("utf-8"));
      byte[] data = stool.sign();
      return Base62.encode(data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 校验签名
   *
   * @param content 待验内容
   * @param signature 签名
   * @param publicKey 公钥
   * @return 是否有效签名
   */
  public static boolean verify(String content, String signature, String publicKey) {
    try {
      Signature stool = Signature.getInstance("MD5WithRSA");
      stool.initVerify(getPublicKey(publicKey));
      stool.update(content.getBytes("utf-8"));
      // 验证签名是否正常
      return stool.verify(Base62.decode(signature));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String encText(String data, String publicKey) {
    try {
      // 获取一个加密算法为RSA的加解密器对象cipher。
      Cipher cipher = Cipher.getInstance("RSA");
      // 设置为加密模式,并将公钥给cipher。
      cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
      // 获得密文
      byte[] bytes = data.getBytes();
      if (bytes.length > 117) {
        byte[][] dataBytes = splitBytes(bytes);
        byte[][] result = new byte[dataBytes.length][];
        for (int i = 0; i < dataBytes.length; i++) {
          result[i] = cipher.doFinal(dataBytes[i]);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
          sb.append(Base62.encode(result[i]));
          if (result.length - 1 != i) {
            sb.append(",");
          }
        }
        return sb.toString();
      } else {
        byte[] secret = cipher.doFinal(bytes);
        // 进行Base62编码
        return Base62.encode(secret);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String decText(String secret, String privateKey) {
    try {
      Cipher cipher = Cipher.getInstance("RSA");
      // 传递私钥，设置为解密模式。
      cipher.init(Cipher.DECRYPT_MODE, RsaUtils.getPrivateKey(privateKey));
      // 解密器解密由Base62解码后的密文,获得明文字节数组
      if (secret.contains(",")) {
        String[] strings = secret.split(",");
        byte[][] bytes = new byte[strings.length][];
        int length = 0;
        for (int i = 0; i < strings.length; i++) {
          bytes[i] = cipher.doFinal(Base62.decode(strings[i]));
          length += bytes[i].length;
        }
        byte[] byteResult = new byte[length];
        int index = 0;
        for (byte[] aByte : bytes) {
          for (byte b : aByte) {
            byteResult[index] = b;
            index++;
          }
        }
        return new String(byteResult);
      } else {
        byte[] b = cipher.doFinal(Base62.decode(secret));
        // 转换成字符串
        return new String(b);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static byte[][] splitBytes(byte[] bytes) {
    double splitLength = 100.0;
    int arrayLength = (int) Math.ceil(bytes.length / splitLength);
    byte[][] result = new byte[arrayLength][];
    int from, to;
    for (int i = 0; i < arrayLength; i++) {

      from = (int) (i * splitLength);
      to = (int) (from + splitLength);
      if (to > bytes.length) {
        to = bytes.length;
      }
      result[i] = Arrays.copyOfRange(bytes, from, to);
    }
    return result;
  }

  private static byte[] toByte(String str) {
    return Base62.decode(str);
  }

  private static String toStr(byte[] bytes) {
    return Base62.encode(bytes);
  }

  public static void main(String[] args) {
    RsaEntity keyPair = createKeyPair();
    String publicKeyStr = keyPair.getPublicKeyStr();
    String privateKeyStr = keyPair.getPrivateKeyStr();
    System.out.println(publicKeyStr);
    System.out.println(privateKeyStr);
    String test =
        "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.";
    String s = encText(test, publicKeyStr);
    System.out.println(s);
    System.out.println(decText(s, privateKeyStr));
    String sign = sign(test, privateKeyStr);
    System.out.println(sign);
    System.out.println(verify(test, sign, publicKeyStr));
  }
}
