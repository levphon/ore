package com.hy.commons.encrypt; 

import java.io.FileWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Description:
 * @author  LiChunming
 * @version V1.0 
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2011
 **/
public class RSAUtil {
	 public static void main(String[] args) throws Exception{
		 KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");   
	        keyPairGen.initialize(1024);   
	        
	        KeyPair keyPair = keyPairGen.generateKeyPair();   
	        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();   
	        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); 
	        byte[] prStr=Base64.encode(publicKey.getEncoded());
	        System.out.println("publicKey:"+Base64.encode(publicKey.getEncoded()));
	        System.out.println("privateKey:"+privateKey);
	        FileWriter fw=new FileWriter( "c:/private_key.dat"); 
            fw.write(prStr.toString());
            fw.close();
	 }

}
 