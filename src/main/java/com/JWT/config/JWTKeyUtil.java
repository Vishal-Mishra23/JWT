package com.JWT.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
public class JWTKeyUtil {

	/**
	 * @param keyName
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public PrivateKey getJwtPrivateKey(String keyName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		InputStream rawKey = JWTKeyUtil.class.getClassLoader().getResourceAsStream(keyName);
		if(rawKey==null) {
			throw new IllegalArgumentException("Resource not found: " + keyName);
		}
		
		String rawPrivateKey = new String(rawKey.readAllBytes() , StandardCharsets.UTF_8 );
		
		rawPrivateKey = rawPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
		
		byte[] keyBytes = Base64.getDecoder().decode(rawPrivateKey);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(keySpec);
		
	}
	
	/**
	 * @param certName
	 * @return
	 * @throws CertificateException
	 */
	public PublicKey getJwtPublicKey(String certName) throws CertificateException {
		InputStream rawCert = JWTKeyUtil.class.getClassLoader().getResourceAsStream(certName);
		if(rawCert==null) {
			throw new IllegalArgumentException("Resource not found: " + certName);
		}
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(rawCert);
		cert.checkValidity();
		return cert.getPublicKey();
	}
}
