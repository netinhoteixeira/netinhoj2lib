package info.netinho.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

public class BrowserDetect {

    protected String userAgent;
    protected String company;
    protected String name;
    protected String version;
    protected int mainVersion;
    protected int moderateVersion;
    protected int minorVersion;
    protected int buildVersion;
    protected String os;
    protected String osArch;
    protected String engine;
    protected String engineVersion;
    protected String language = "en";
    protected Locale locale;
    private HashMap<String, String> supportedLanguages;

    public BrowserDetect(String userAgent, String acceptLanguage) {
        initialize();
        if ((userAgent != null) && (!userAgent.isEmpty())) {
            setUserAgent(userAgent.toLowerCase());
        }
        if ((acceptLanguage != null) && (!acceptLanguage.isEmpty())) {
            setLanguage(acceptLanguage.toLowerCase());
        }
        setLocale();
    }

    private void initialize() {
        this.supportedLanguages = new HashMap<String, String>(2);
        this.supportedLanguages.put(this.language, "");
    }

    private void setUserAgent(String httpUserAgent) {
        this.userAgent = httpUserAgent.toLowerCase();
        setCompany();
        setName();
        setVersion();
        setOS();
        setOSArch();
        setEngine();
        setEngineVersion();
    }

    private void setCompany() {
        if (this.userAgent != null) {
            if ((this.userAgent.indexOf("android") > -1) || (this.userAgent.indexOf("chrome") > -1)) {
                this.company = "Google Inc.";
            } else if (this.userAgent.indexOf("msie") > -1) {
                this.company = "Microsoft";
            } else if (this.userAgent.indexOf("opera") > -1) {
                this.company = "Opera Software";
            } else if (this.isAppleSafari()) {
                this.company = "Apple Inc.";
            } else if (this.userAgent.indexOf("mozilla") > -1) {
                this.company = "Mozilla Foundation";
            } else {
                this.company = "unknown";
            }
        }
    }

    public String getCompany() {
        return this.company;
    }

    private void setName() {
        if ((this.company.equals("Google Inc.")) && (this.userAgent.indexOf("android") > -1)) {
            this.name = "Android";
        } else if ((this.company.equals("Google Inc.")) && (this.userAgent.indexOf("chrome") > -1)) {
            this.name = "Google Chrome";
        } else if (this.company.equals("Microsoft")) {
            this.name = "Microsoft Internet Explorer";
        } else if (this.company.equals("Netscape Communications")) {
            this.name = "Netscape Navigator";
        } else if (this.company.equals("Opera Software")) {
            this.name = "Opera";
        } else if (this.company.equals("Mozilla Foundation")) {
            this.name = "Mozilla Firefox";
        } else if ((this.company.equals("Apple Inc.")) && (this.userAgent.indexOf("safari") > -1)) {
            this.name = "Safari";
        } else {
            this.name = "unknown";
        }
    }

    public String getName() {
        return this.name;
    }

    private void setVersion() {
        if (isGoogleChrome()) {
            String tmpString = this.userAgent.substring(this.userAgent.indexOf("chrome") + 7).trim();
            this.version = tmpString.substring(0, tmpString.indexOf(" ")).trim();
        } else if (this.company.equals("Microsoft")) {
            String str = this.userAgent.substring(this.userAgent.indexOf("msie") + 5);
            this.version = str.substring(0, str.indexOf(";"));
        } else if (isMozillaFirefox()) {
            String tmpString = this.userAgent.substring(this.userAgent.indexOf("firefox") + 8).trim();
            if (tmpString.indexOf(" ") > 0) {
                this.version = tmpString.substring(0, tmpString.indexOf(" ")).trim();
            } else {
                this.version = tmpString;
            }
        } else if (isOpera() || isAppleSafari() || isGoogleAndroid()) {
            String tmpString = this.userAgent.substring(this.userAgent.indexOf("version") + 8).trim();
            if (tmpString.indexOf(" ") > 0) {
                this.version = tmpString.substring(0, tmpString.indexOf(" ")).trim();
            } else {
                this.version = tmpString;
            }
        } else {
            try {
                int tmpPos;
                String tmpString = this.userAgent.substring(tmpPos = this.userAgent.indexOf("/") + 1, tmpPos + this.userAgent.indexOf(" ")).trim();
                this.version = !tmpString.isEmpty() ? (tmpString.indexOf(" ") != -1 ? tmpString.substring(0, tmpString.indexOf(" ")) : tmpString) : new String();
            } catch (StringIndexOutOfBoundsException ex) {
                this.version = new String();
            }
        }
        

        if (!this.version.isEmpty()) {
            if (this.version.startsWith("/")) {
                this.version = this.version.substring(1);
            }
            
            String tmpString = this.version;
            this.mainVersion = 0;
            this.moderateVersion = 0;
            this.minorVersion = 0;
            this.buildVersion = 0;
            try {
                if (tmpString.indexOf(".") != -1) {
                    this.mainVersion = Integer.parseInt(tmpString.substring(0, tmpString.indexOf(".")));
                    tmpString = tmpString.substring(tmpString.indexOf(".") + 1).trim();
                } else {
                    this.mainVersion = Integer.parseInt(tmpString);
                }

                if (tmpString.indexOf(".") != -1) {
                    this.moderateVersion = Integer.parseInt(tmpString.substring(0, tmpString.indexOf(".")));
                    tmpString = tmpString.substring(tmpString.indexOf(".") + 1).trim();
                } else if (tmpString.indexOf(" ") != -1) {
                    this.moderateVersion = Integer.parseInt(tmpString.substring(0, tmpString.indexOf(" ")));
                    tmpString = tmpString.substring(tmpString.indexOf(" ") + 1).trim();
                } else {
                    this.moderateVersion = Integer.parseInt(tmpString);
                }

                if (tmpString.indexOf(".") != -1) {
                    this.minorVersion = Integer.parseInt(tmpString.substring(0, tmpString.indexOf(".")));
                    tmpString = tmpString.substring(tmpString.indexOf(".") + 1).trim();
                } else if (tmpString.indexOf(" ") != -1) {
                    this.minorVersion = Integer.parseInt(tmpString.substring(0, tmpString.indexOf(" ")));
                    tmpString = tmpString.substring(tmpString.indexOf(" ") + 1).trim();
                } else {
                    this.minorVersion = Integer.parseInt(tmpString);
                }

                if (tmpString.indexOf(".") != -1) {
                    this.buildVersion = Integer.parseInt(tmpString.substring(0, tmpString.indexOf(".")));
                } else if (tmpString.indexOf(" ") != -1) {
                    this.buildVersion = Integer.parseInt(tmpString.substring(0, tmpString.indexOf(" ")));
                } else {
                    this.buildVersion = Integer.parseInt(tmpString);
                }
            } catch (NumberFormatException ex) {
            }
        }
    }

    public String getVersion() {
        return this.version;
    }

    public int getMainVersion() {
        return this.mainVersion;
    }

    public int getModerateVersion() {
        return this.moderateVersion;
    }

    public int getMinorVersion() {
        return this.minorVersion;
    }

    public int getBuildVersion() {
        return this.buildVersion;
    }

    private void setOS() {
        if (this.userAgent != null) {
            if (this.userAgent.indexOf("linux") > -1) {
                this.os = "Linux";
            } else if (this.userAgent.indexOf("win") > -1) {
                if ((this.userAgent.indexOf("windows 95") > -1) || (this.userAgent.indexOf("win95") > -1)) {
                    this.os = "Windows 95";
                }
                if ((this.userAgent.indexOf("windows 98") > -1) || (this.userAgent.indexOf("win98") > -1)) {
                    this.os = "Windows 98";
                }
                if ((this.userAgent.indexOf("windows nt") > -1) || (this.userAgent.indexOf("winnt") > -1)) {
                    this.os = "Windows NT";
                }
                if ((this.userAgent.indexOf("win16") > -1) || (this.userAgent.indexOf("windows 3.") > -1)) {
                    this.os = "Windows 3.x";
                }
                if (this.userAgent.indexOf("windows nt 6.1") > -1) {
                    this.os = "Windows 7";
                }
            } else if (this.userAgent.indexOf("like mac os") > -1) {
                this.os = "iOS " + this.userAgent.substring(this.userAgent.indexOf(" os ") + 4, this.userAgent.indexOf("like mac os") - 1);
            }
        }
    }

    public String getOS() {
        return this.os;
    }

    private void setOSArch() {
        if (this.userAgent != null) {
            if (this.userAgent.indexOf("x86_64") > -1) {
                this.osArch = "64";
            } else {
                this.osArch = "32";
            }
            this.osArch += " bits";
        }
    }

    public String getOSArch() {
        return this.osArch;
    }

    private void setEngine() {
        if (this.isGecko()) {
            this.engine = "Gecko";
        } else if (this.isAppleWebKit()) {
            this.engine = "AppleWebKit";
        } else if (this.isTrident()) {
            this.engine = "Trident";
        } else if (this.isPresto()) {
            this.engine = "Presto";
        } else {
            this.engine = "unknown";
        }
    }

    public String getEngine() {
        return this.engine;
    }

    private void setEngineVersion() {
        if (!this.engine.equals("unknown")) {
            this.engineVersion = this.userAgent.substring(this.userAgent.indexOf(this.engine.toLowerCase()) + this.engine.length() + 1);
            if (this.engineVersion.indexOf(";") > -1) {
                this.engineVersion = this.engineVersion.substring(0, this.engineVersion.indexOf(";"));
            }
            if (this.engineVersion.indexOf(")") > -1) {
                this.engineVersion = this.engineVersion.substring(0, this.engineVersion.indexOf(")"));
            }
            if (this.engineVersion.indexOf(" ") > -1) {
                this.engineVersion = this.engineVersion.substring(0, this.engineVersion.indexOf(" "));
            }
            this.engineVersion = this.engineVersion.trim();
        }
    }

    public String getEngineVersion() {
        return this.engineVersion;
    }

    private void setLanguage(String acceptLanguage) {
        String prefLanguage = acceptLanguage;

        if (prefLanguage != null) {
            String tmplanguage;
            StringTokenizer st = new StringTokenizer(prefLanguage, ",");

            int elements = st.countTokens();
            for (int idx = 0; idx < elements; idx++) {
                if (this.supportedLanguages.containsKey(tmplanguage = st.nextToken())) {
                    this.language = parseLocale(tmplanguage);
                }
            }
        }
    }

    private String parseLocale(String language) {
        StringTokenizer st = new StringTokenizer(language, "-");

        if (st.countTokens() == 2) {
            return st.nextToken();
        }
        return language;
    }

    public String getLanguage() {
        return this.language;
    }

    private void setLocale() {
        this.locale = new Locale(this.language, "");
    }

    public Locale getLocale() {
        return this.locale;
    }

    public boolean isGecko() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("gecko") != -1 && this.userAgent.indexOf("safari") == -1;
        } else {
            return false;
        }
    }

    public boolean isAppleWebKit() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("applewebkit") != -1;
        } else {
            return false;
        }
    }

    public boolean isTrident() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("trident") != -1;
        } else {
            return false;
        }
    }

    public boolean isPresto() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("presto") != -1;
        } else {
            return false;
        }
    }

    public boolean isAppleiPod() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("ipod") != -1;
        } else {
            return false;
        }
    }

    public boolean isAppleiPhone() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("iphone") != -1;
        } else {
            return false;
        }
    }

    public boolean isAppleiPad() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("ipad") != -1;
        } else {
            return false;
        }
    }

    public boolean isGoogleAndroid() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("android") != -1;
        } else {
            return false;
        }
    }

    public boolean isMobile() {
        return (this.userAgent.indexOf("mobile") != -1) || this.isAppleiPod() || this.isAppleiPhone() || this.isAppleiPad() || this.isGoogleAndroid();
    }
    
    public String getMobile() {
        if (this.isMobile()) {
            if (this.isAppleiPod()) {
                return "iPod";
            } else if (this.isAppleiPhone()) {
                return "iPhone";
            } else if (this.isAppleiPad()) {
                return "iPad";
            } else if (this.isGoogleAndroid()) {
                return "Android";
            } else {
                return "unknown";
            }
        }
        return null;
    }

    public boolean isMozillaFirefox() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("firefox") != -1;
        } else {
            return false;
        }
    }

    public boolean isMicrosoftInternetExplorer() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("msie") != -1;
        } else {
            return false;
        }
    }

    public boolean isGoogleChrome() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("chrome") != -1;
        } else {
            return false;
        }
    }

    public boolean isOpera() {
        if (this.userAgent != null) {
            return this.userAgent.indexOf("opera") != -1;
        } else {
            return false;
        }
    }

    public boolean isAppleSafari() {
        if (this.userAgent != null) {
            return (this.userAgent.indexOf("safari") != -1) && (this.userAgent.indexOf("chrome") == -1);
        } else {
            return false;
        }
    }

    public boolean isOldBrowser() {
        boolean old = true;
        if (this.isMozillaFirefox()) {
            old = this.getMainVersion() < 5;
        } else if (this.isMicrosoftInternetExplorer()) {
            old = this.getMainVersion() < 9;
        } else if (this.isGoogleChrome()) {
            old = this.getMainVersion() < 12;
        } else if (this.isAppleSafari() && (!this.isMobile())) {
            old = this.getMainVersion() < 5;
        } else if (this.isAppleSafari()) {
            old = this.getMainVersion() < 5;
        } else if (this.isGoogleAndroid()) {
            old = this.getMainVersion() < 4;
        } else if (this.isOpera()) {
            old = this.getMainVersion() < 11;
        }
        return old;
    }
}