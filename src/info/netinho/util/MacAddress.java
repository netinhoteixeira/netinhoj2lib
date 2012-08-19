/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.netinho.util;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author Francisco Ernesto Teixeira <contato@netinho.info>
 */
public class MacAddress {

    public static void main(String[] args) {
        System.out.println("Output of Network Interrogation:");
        System.out.println("********************************");
        System.out.print(outputNetworkInterrogation());
    }

    public static String outputNetworkInterrogation() {
        String result = new String();

        try {
            InetAddress theLocalhost = InetAddress.getLocalHost();
            result += " LOCALHOST INFO\n";
            if (theLocalhost != null) {
                result += "          host: " + theLocalhost.getHostName() + "\n";
                result += "         class: " + theLocalhost.getClass().getSimpleName() + "\n";
                result += "            ip: " + theLocalhost.getHostAddress() + "\n";
                result += "         chost: " + theLocalhost.getCanonicalHostName() + "\n";
                result += "      byteaddr: " + toMACAddrString(theLocalhost.getAddress()) + "\n";
                result += "    sitelocal?: " + theLocalhost.isSiteLocalAddress() + "\n";
                result += "\n";
            } else {
                result += " localhost was null\n";
            }

            Enumeration<NetworkInterface> theIntfList = NetworkInterface.getNetworkInterfaces();
            List<InterfaceAddress> theAddrList;
            NetworkInterface theIntf;
            InetAddress theAddr;

            while (theIntfList.hasMoreElements()) {
                theIntf = theIntfList.nextElement();

                result += "--------------------" + "\n";
                result += " " + theIntf.getDisplayName() + "\n";
                result += "          name: " + theIntf.getName() + "\n";
                result += "           mac: " + toMACAddrString(theIntf.getHardwareAddress()) + "\n";
                result += "           mtu: " + theIntf.getMTU() + "\n";
                result += "        mcast?: " + theIntf.supportsMulticast() + "\n";
                result += "     loopback?: " + theIntf.isLoopback() + "\n";
                result += "          ptp?: " + theIntf.isPointToPoint() + "\n";
                result += "      virtual?: " + theIntf.isVirtual() + "\n";
                result += "           up?: " + theIntf.isUp() + "\n";

                theAddrList = theIntf.getInterfaceAddresses();
                result += "     int addrs: " + theAddrList.size() + " total.\n";
                int addrindex = 0;
                for (InterfaceAddress intAddr : theAddrList) {
                    addrindex++;
                    theAddr = intAddr.getAddress();
                    result += "            " + addrindex + ").\n";
                    result += "            host: " + theAddr.getHostName() + "\n";
                    result += "           class: " + theAddr.getClass().getSimpleName() + "\n";
                    result += "              ip: " + theAddr.getHostAddress() + "/" + intAddr.getNetworkPrefixLength() + "\n";
                    if ((intAddr.getBroadcast() != null) && (intAddr.getBroadcast().getHostAddress() != null)) {
                        result += "           bcast: " + intAddr.getBroadcast().getHostAddress() + "\n";
                    }
                    int maskInt = Integer.MIN_VALUE >> (intAddr.getNetworkPrefixLength() - 1);
                    result += "            mask: " + toIPAddrString(maskInt) + "\n";
                    result += "           chost: " + theAddr.getCanonicalHostName() + "\n";
                    result += "        byteaddr: " + toMACAddrString(theAddr.getAddress()) + "\n";
                    result += "      sitelocal?: " + theAddr.isSiteLocalAddress() + "\n";
                    result += "\n";
                }
            }
        } catch (SocketException e) {
            result += e.getMessage() + "\n";
        } catch (UnknownHostException e) {
            result += e.getMessage() + "\n";
        }

        return result;
    }

    private static String toMACAddrString(byte[] a) {
        if (a == null) {
            return "null";
        }
        int iMax = a.length - 1;

        if (iMax == -1) {
            return "[]";
        }

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0;; i++) {
            b.append(String.format("%1$02x", a[i]));

            if (i == iMax) {
                return b.append(']').toString();
            }
            b.append(":");
        }
    }

    private static String toIPAddrString(int ipa) {
        StringBuilder b = new StringBuilder();
        b.append(Integer.toString(0x000000ff & (ipa >> 24)));
        b.append(".");
        b.append(Integer.toString(0x000000ff & (ipa >> 16)));
        b.append(".");
        b.append(Integer.toString(0x000000ff & (ipa >> 8)));
        b.append(".");
        b.append(Integer.toString(0x000000ff & (ipa)));
        return b.toString();
    }

    public static boolean isValidIP(String ip) {
        if (ip == null) {
            return false;
        }
        if (ip.trim().equals("")) {
            return false;
        }
        if (ip.indexOf("-") >= 0) {
            return false;
        }
        String[] strPartes = ip.replace('.', '-').split("-");
        if (strPartes.length != 4) {
            return false;
        }
        for (int i = 0; i < strPartes.length; i++) {
            String strPedaco = strPartes[i];
            if (strPedaco == null) {
                return false;
            }
            if (strPedaco.trim().equals("")) {
                return false;
            }
            try {
                int intPedaco = Integer.parseInt(strPedaco);
                if ((intPedaco == 0) || (intPedaco >= 254)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private static String getIP() throws SocketException, UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();

        if (ip.equals("127.0.0.1")) {
            Enumeration<NetworkInterface> theIntfList = NetworkInterface.getNetworkInterfaces();
            List<InterfaceAddress> theAddrList;

            while (theIntfList.hasMoreElements()) {
                theAddrList = theIntfList.nextElement().getInterfaceAddresses();
                for (InterfaceAddress intAddr : theAddrList) {
                    if (isValidIP(intAddr.getAddress().getHostAddress()) && !intAddr.getAddress().getHostAddress().equals("127.0.0.1")) {
                        ip = intAddr.getAddress().getHostAddress();
                        break;
                    }
                }
            }
        }

        return ip;
    }

    public static String get(String ip) throws SocketException, UnknownHostException {
        String result = new String();

        InetAddress address = InetAddress.getByName(ip);

        /*
         * Get NetworkInterface for the current host and then read the hardware
         * address.
         */
        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
        if (ni != null) {
            byte[] mac = ni.getHardwareAddress();
            if (mac != null) {
                /*
                 * Extract each array of mac address and convert it to hexa with
                 * the following format 08-00-27-DC-4A-9E.
                 */
                for (int i = 0; i < mac.length; i++) {
                    result += String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : "");
                }
            } else {
                // Address doesn't exist or is not accessible.
                throw new UnknownHostException(ip);
            }
        } else {
            // Network Interface for the specified address is not found.
            throw new UnknownHostException(ip);
        }

        return result;
    }

    public static String get() throws SocketException, UnknownHostException {
        return get(getIP());
    }

    public static String getOnlyCode(String ip) throws SocketException, UnknownHostException {
        return get(ip).replace(":", "");
    }

    public static String getOnlyCode() throws SocketException, UnknownHostException {
        return getOnlyCode(getIP());
    }
}