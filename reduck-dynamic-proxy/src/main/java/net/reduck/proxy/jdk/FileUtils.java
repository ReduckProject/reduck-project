package net.reduck.proxy.jdk;

import java.io.FileOutputStream;
import java.util.Base64;

/**
 * @author Gin
 * @since 2023/2/6 10:39
 */
public class FileUtils {

    public static void write(String name, byte[] data) {
        try {
            FileOutputStream os = new FileOutputStream(System.getProperty("user.dir") + name);
            os.write(data);
            os.close();
        } catch (Exception e) {

        }

    }

    public static void main(String[] args) {
        String data =
                "yv66vgAAADEAbQEABjxpbml0PgEAKChMamF2YS9sYW5nL3JlZmxlY3QvSW52b2NhdGlvbkhhbmRsZXI7KVYBAARDb2RlAQAKRXhjZXB0aW9ucwEAF2phdmEvbGFuZy9yZWZsZWN0L1Byb3h5BwAFDAABAAIKAAYABwEAAm0xAQAaTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBAAZlcXVhbHMBABUoTGphdmEvbGFuZy9PYmplY3Q7KVoBAAFoAQAlTGphdmEvbGFuZy9yZWZsZWN0L0ludm9jYXRpb25IYW5kbGVyOwwADQAOCQAGAA8BABVjb20vc3VuL3Byb3h5LyRQcm94eTAHABEMAAkACgkAEgATAQAQamF2YS9sYW5nL09iamVjdAcAFQEAI2phdmEvbGFuZy9yZWZsZWN0L0ludm9jYXRpb25IYW5kbGVyBwAXAQAGaW52b2tlAQBTKExqYXZhL2xhbmcvT2JqZWN0O0xqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7W0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsMABkAGgsAGAAbAQARamF2YS9sYW5nL0Jvb2xlYW4HAB0BAAxib29sZWFuVmFsdWUBAAMoKVoMAB8AIAoAHgAhAQAPamF2YS9sYW5nL0Vycm9yBwAjAQAaamF2YS9sYW5nL1J1bnRpbWVFeGNlcHRpb24HACUBABNqYXZhL2xhbmcvVGhyb3dhYmxlBwAnAQAuamF2YS9sYW5nL3JlZmxlY3QvVW5kZWNsYXJlZFRocm93YWJsZUV4Y2VwdGlvbgcAKQEAGChMamF2YS9sYW5nL1Rocm93YWJsZTspVgwAAQArCgAqACwBAAJtNAEAB2dldE5hbWUBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwwALgAKCQASADEBABBqYXZhL2xhbmcvU3RyaW5nBwAzAQACbTIBAAh0b1N0cmluZwwANQAKCQASADcBAAJtMwEABmdldEFnZQEAAygpSQwAOQAKCQASADwBABFqYXZhL2xhbmcvSW50ZWdlcgcAPgEACGludFZhbHVlDABAADsKAD8AQQEAAm0wAQAIaGFzaENvZGUMAEMACgkAEgBFAQAIPGNsaW5pdD4BAAMoKVYBABBqYXZhLmxhbmcuT2JqZWN0CABJAQAPamF2YS9sYW5nL0NsYXNzBwBLAQAHZm9yTmFtZQEAJShMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9DbGFzczsMAE0ATgoATABPCAALAQAJZ2V0TWV0aG9kAQBAKExqYXZhL2xhbmcvU3RyaW5nO1tMamF2YS9sYW5nL0NsYXNzOylMamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kOwwAUgBTCgBMAFQBAB9uZXQucmVkdWNrLnByb3h5Lmpkay5Vc2VTZXJ2aWNlCABWCAAvCAA2CAA6CABEAQAfamF2YS9sYW5nL05vU3VjaE1ldGhvZEV4Y2VwdGlvbgcAXAEAG2phdmEvbGFuZy9Ob1N1Y2hNZXRob2RFcnJvcgcAXgEACmdldE1lc3NhZ2UMAGAAMAoAKABhAQAVKExqYXZhL2xhbmcvU3RyaW5nOylWDAABAGMKAF8AZAEAIGphdmEvbGFuZy9DbGFzc05vdEZvdW5kRXhjZXB0aW9uBwBmAQAeamF2YS9sYW5nL05vQ2xhc3NEZWZGb3VuZEVycm9yBwBoCgBpAGQBAB9uZXQvcmVkdWNrL3Byb3h5L2pkay9Vc2VTZXJ2aWNlBwBrABEAEgAGAAEAbAAFAAoACQAKAAAACgAuAAoAAAAKADUACgAAAAoAOQAKAAAACgBDAAoAAAAHAAEAAQACAAIAAwAAABIACgACAAAABiortwAIsQAAAAAABAAAAAIAAAARAAsADAACAAMAAABLAAoAAwAAACcqtAAQKrIAFAS9ABZZAytTuQAcBADAAB62ACKsv027ACpZLLcALb8AAwAAABwAHAAkAAAAHAAcACYAAAAcAB0AKAAAAAQAAAACAAAAEQAvADAAAgADAAAAQQAKAAIAAAAdKrQAECqyADIBuQAcBADAADSwv0y7ACpZK7cALb8AAwAAABIAEgAkAAAAEgASACYAAAASABMAKAAAAAQAAAACAAAAEQA2ADAAAgADAAAAQQAKAAIAAAAdKrQAECqyADgBuQAcBADAADSwv0y7ACpZK7cALb8AAwAAABIAEgAkAAAAEgASACYAAAASABMAKAAAAAQAAAACAAAAEQA6ADsAAgADAAAARAAKAAIAAAAgKrQAECqyAD0BuQAcBADAAD+2AEKsv0y7ACpZK7cALb8AAwAAABUAFQAkAAAAFQAVACYAAAAVABYAKAAAAAQAAAACAAAAEQBEADsAAgADAAAARAAKAAIAAAAgKrQAECqyAEYBuQAcBADAAD+2AEKsv0y7ACpZK7cALb8AAwAAABUAFQAkAAAAFQAVACYAAAAVABYAKAAAAAQAAAACAAAACABHAEgAAgADAAAAlAAKAAIAAAB4Ekq4AFASUQS9AExZAxJKuABQU7YAVbMAFBJXuABQElgDvQBMtgBVswAyEkq4AFASWQO9AEy2AFWzADgSV7gAUBJaA70ATLYAVbMAPRJKuABQElsDvQBMtgBVswBGsUy7AF9ZK7YAYrcAZb9MuwBpWSu2AGK3AGq/AAIAAABeAF4AXQAAAF4AawBnAAAABAAAAAIAAAAA"
;

        FileUtils.write("Proxy0.class", Base64.getDecoder().decode(data));
    }
}