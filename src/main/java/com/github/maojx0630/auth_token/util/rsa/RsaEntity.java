package com.github.maojx0630.auth_token.util.rsa;

import com.github.maojx0630.auth_token.util.Base62;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 保存rsa公私钥字符串
 *
 * @author MaoJiaXing
 * @since 2020-04-20 10:23
 */
public class RsaEntity {

  private final PublicKey publicKey;

  private final String publicKeyStr;
  private final PrivateKey privateKey;

  private final String privateKeyStr;

  public RsaEntity(PublicKey publicKey, PrivateKey privateKey) {
    this.publicKey = publicKey;
    this.privateKey = privateKey;
    this.publicKeyStr = Base62.encode(publicKey.getEncoded());
    this.privateKeyStr = Base62.encode(privateKey.getEncoded());
  }

  public PublicKey getPublicKey() {
    return publicKey;
  }

  public String getPublicKeyStr() {
    return publicKeyStr;
  }

  public PrivateKey getPrivateKey() {
    return privateKey;
  }

  public String getPrivateKeyStr() {
    return privateKeyStr;
  }
}
