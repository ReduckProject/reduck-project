package net.reduck.data.protection.env;

/**
 * @author Gin
 * @since 2023/5/23 19:46
 */
public class PropertiesProtector {
    private final EncryptionWrapperDetector wrapperDetector = new EncryptionWrapperDetector("$ENC{", "}");
    private final AesEncryptor encryptor;

    public PropertiesProtector(String secretKey) {
        this.encryptor = new AesEncryptor(PrivateKeyFinder.getSecretKey(secretKey));
    }

    public String getSecurityProperty(String property) {
        return wrapperDetector.wrapper(encryptor.encrypt(property));
    }

    public static void main(String[] args) {
        System.out.println(generateSecretKey());
        String secretKey = "kjZthuWrVrKDZmRLVl+sZav3N/01IqWggoHQKHLVXxm/YpFnXWHSoECwQIRpVeh+u/mNfHs+fOL4g/MCsyoSMdmBTY56cfQxMzdKxpPRvWG0jpQe/cM/9CU7mYN0H06Bu8RuP74H4C0oPo5MsTGqJQhc9Z2m2xpLRM+PyLDRjD1weZck+BBq78mPsRS5/kscmGSnfpZCno3iWHe7OQq34o4DhfOELhzVh1ke0fzJ0lvc8CZyuIXMxGyVVMwdVs3vz1IqwvzczowWSXmQwNaW6uBc6Ur2aI3uCCMrjvX3hV1SbHqhHQvG3jDP8fvieEtYa3mXeRuiO6aJMaR7ohqXfw==";
        System.out.println(new PropertiesProtector(secretKey).getSecurityProperty("ginsco333"));
    }

    public static String generateSecretKey() {
        return PrivateKeyFinder.generateSecretKey();
    }
}
