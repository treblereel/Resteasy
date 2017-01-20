package org.jboss.resteasy.jose;

import java.util.Base64;

import org.jboss.resteasy.jose.i18n.Messages;
/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class Base64Url
{
   public static String encode(byte[] bytes)
   {
      String s = Base64.getEncoder().encodeToString(bytes);
      s = s.split("=")[0]; // Remove any trailing '='s
      s = s.replace('+', '-'); // 62nd char of encoding
      s = s.replace('/', '_'); // 63rd char of encoding
      return s;
   }

   public static byte[] decode(String s)
   {
      s = s.replace('-', '+'); // 62nd char of encoding
      s = s.replace('_', '/'); // 63rd char of encoding
      switch (s.length() % 4) // Pad with trailing '='s
      {
         case 0: break; // No pad chars in this case
         case 2: s += "=="; break; // Two pad chars
         case 3: s += "="; break; // One pad char
         default: throw new RuntimeException(Messages.MESSAGES.illegalBase64UrlString());
      }
      try
      {
         return Base64.getDecoder().decode(s);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }


}
