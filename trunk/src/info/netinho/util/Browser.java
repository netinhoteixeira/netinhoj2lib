/**
 * Converted from file: Browser.php Author: Chris Schuld
 * (http://chrisschuld.com/) Last Modified: August 20th, 2010
 *
 * @version 1.9 @package PegasusPHP
 *
 * Copyright (C) 2008-2010 Chris Schuld (chris@chrisschuld.com)
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details
 * at: http://www.gnu.org/copyleft/gpl.html
 *
 *
 * Typical Usage:
 *
 * $browser = new Browser(); if( $browser->getBrowser() ==
 * Browser::BROWSER_FIREFOX && $browser->getVersion() >= 2 ) { echo "You have
 * FireFox version 2 or greater"; }
 *
 * User Agents Sampled from: http://www.useragentstring.com/
 *
 * This implementation is based on the original work from Gary White
 * http://apptools.com/phptools/browser/
 *
 * UPDATES:
 *
 * 2010-08-20 (v1.9): + Added MSN Explorer Browser (legacy) + Added Bing/MSN
 * Robot (Thanks Rob MacDonald) + Added the Android Platform (PLATFORM_ANDROID)
 * + Fixed issue with Android 1.6/2.2 (Thanks Tom Hirashima)
 *
 * 2010-04-27 (v1.8): + Added iPad Support
 *
 * 2010-03-07 (v1.7): + *MAJOR* Rebuild (preg_match and other "slow" routine
 * removal(s)) + Almost allof Gary"s original code has been replaced + Large
 * PHPUNIT testing environment created to validate new releases and additions +
 * Added FreeBSD Platform + Added OpenBSD Platform + Added NetBSD Platform +
 * Added SunOS Platform + Added OpenSolaris Platform + Added support of the
 * Iceweazel Browser + Added isChromeFrame() call to check if chromeframe is in
 * use + Moved the Opera check in front of the Firefox check due to legacy Opera
 * User Agents + Added the __toString() method (Thanks Deano)
 *
 * 2009-11-15: + Updated the checkes for Firefox + Added the NOKIA platform +
 * Added Checks for the NOKIA brower(s)
 *
 * 2009-11-08: + PHP 5.3 Support + Added support for BlackBerry OS and
 * BlackBerry browser + Added support for the Opera Mini browser + Added
 * additional documenation + Added support for isRobot() and isMobile() + Added
 * support for Opera version 10 + Added support for deprecated Netscape
 * Navigator version 9 + Added support for IceCat + Added support for Shiretoko
 *
 * 2010-04-27 (v1.8): + Added iPad Support
 *
 * 2009-08-18: + Updated to support PHP 5.3 - removed all deprecated function
 * calls + Updated to remove all double quotes (") -- converted to single quotes
 * (")
 *
 * 2009-04-27: + Updated the IE check to remove a typo and bug (thanks John)
 *
 * 2009-04-22: + Added detection for GoogleBot + Added detection for the W3C
 * Validator. + Added detection for Yahoo! Slurp
 *
 * 2009-03-14: + Added detection for iPods. + Added Platform detection for
 * iPhones + Added Platform detection for iPods
 *
 * 2009-02-16: (Rick Hale) + Added version detection for Android phones.
 *
 * 2008-12-09: + Removed unused private static String ant
 *
 * 2008-11-07: + Added Google"s Chrome to the detection list + Added
 * isBrowser(string) to the list of functions special thanks to Daniel "mavrick"
 * Lang for the function concept (http://mavrick.id.au)
 *
 *
 * Gary White noted: "Since browser detection is so unreliable, I am no longer
 * maintaining this script. You are free to use and or modify/update it as you
 * want, however the author assumes no responsibility for the accuracy of the
 * detected values."
 *
 * Anyone experienced with Gary"s script might be interested in these notes:
 *
 * Added class private static String ants Added detection and version detection
 * for Google"s Chrome Updated the version detection for Amaya Updated the
 * version detection for Firefox Updated the version detection for Lynx Updated
 * the version detection for WebTV Updated the version detection for NetPositive
 * Updated the version detection for IE Updated the version detection for
 * OmniWeb Updated the version detection for iCab Updated the version detection
 * for Safari Updated Safari to remove mobile devices (iPhone) Added detection
 * for iPhone Added detection for robots Added detection for mobile devices
 * Added detection for BlackBerry Removed Netscape checks (matches heavily with
 * firefox & mozilla)
 *
 */
package info.netinho.util;

/**
 *
 * @author Francisco Ernesto Teixeira <fco.ernesto@gmail.com>
 */
public final class Browser {

    private String $_agent = "";
    private String $_browser_name = "";
    private String $_version = "";
    private String $_platform = "";
    private String $_os = "";
    private boolean $_is_aol = false;
    private boolean $_is_mobile = false;
    private boolean $_is_robot = false;
    private String $_aol_version = "";
    private static String BROWSER_UNKNOWN = "unknown";
    private static String VERSION_UNKNOWN = "unknown";
    private static String BROWSER_OPERA = "Opera";                            // http://www.opera.com/
    private static String BROWSER_OPERA_MINI = "Opera Mini";                  // http://www.opera.com/mini/
    private static String BROWSER_WEBTV = "WebTV";                            // http://www.webtv.net/pc/
    private static String BROWSER_IE = "Internet Explorer";                   // http://www.microsoft.com/ie/
    private static String BROWSER_POCKET_IE = "Pocket Internet Explorer";     // http://en.wikipedia.org/wiki/Internet_Explorer_Mobile
    private static String BROWSER_KONQUEROR = "Konqueror";                    // http://www.konqueror.org/
    private static String BROWSER_ICAB = "iCab";                              // http://www.icab.de/
    private static String BROWSER_OMNIWEB = "OmniWeb";                        // http://www.omnigroup.com/applications/omniweb/
    private static String BROWSER_FIREBIRD = "Firebird";                      // http://www.ibphoenix.com/
    private static String BROWSER_FIREFOX = "Firefox";                        // http://www.mozilla.com/en-US/firefox/firefox.html
    private static String BROWSER_ICEWEASEL = "Iceweasel";                    // http://www.geticeweasel.org/
    private static String BROWSER_SHIRETOKO = "Shiretoko";                    // http://wiki.mozilla.org/Projects/shiretoko
    private static String BROWSER_MOZILLA = "Mozilla";                        // http://www.mozilla.com/en-US/
    private static String BROWSER_AMAYA = "Amaya";                            // http://www.w3.org/Amaya/
    private static String BROWSER_LYNX = "Lynx";                              // http://en.wikipedia.org/wiki/Lynx
    private static String BROWSER_SAFARI = "Safari";                          // http://apple.com
    private static String BROWSER_IPHONE = "iPhone";                          // http://apple.com
    private static String BROWSER_IPOD = "iPod";                              // http://apple.com
    private static String BROWSER_IPAD = "iPad";                              // http://apple.com
    private static String BROWSER_CHROME = "Chrome";                          // http://www.google.com/chrome
    private static String BROWSER_ANDROID = "Android";                        // http://www.android.com/
    private static String BROWSER_GOOGLEBOT = "GoogleBot";                    // http://en.wikipedia.org/wiki/Googlebot
    private static String BROWSER_SLURP = "Yahoo! Slurp";                     // http://en.wikipedia.org/wiki/Yahoo!_Slurp
    private static String BROWSER_W3CVALIDATOR = "W3C Validator";             // http://validator.w3.org/
    private static String BROWSER_BLACKBERRY = "BlackBerry";                  // http://www.blackberry.com/
    private static String BROWSER_ICECAT = "IceCat";                          // http://en.wikipedia.org/wiki/GNU_IceCat
    private static String BROWSER_NOKIA_S60 = "Nokia S60 OSS Browser";        // http://en.wikipedia.org/wiki/Web_Browser_for_S60
    private static String BROWSER_NOKIA = "Nokia Browser";                    // * all other WAP-based browsers on the Nokia Platform
    private static String BROWSER_MSN = "MSN Browser";                        // http://explorer.msn.com/
    private static String BROWSER_MSNBOT = "MSN Bot";                         // http://search.msn.com/msnbot.htm
    // http://en.wikipedia.org/wiki/Msnbot  (used for Bing as well)
    private static String BROWSER_NETSCAPE_NAVIGATOR = "Netscape Navigator";  // http://browser.netscape.com/ (DEPRECATED)
    private static String BROWSER_GALEON = "Galeon";                          // http://galeon.sourceforge.net/ (DEPRECATED)
    private static String BROWSER_NETPOSITIVE = "NetPositive";                // http://en.wikipedia.org/wiki/NetPositive (DEPRECATED)
    private static String BROWSER_PHOENIX = "Phoenix";                        // http://en.wikipedia.org/wiki/History_of_Mozilla_Firefox (DEPRECATED)
    private static String PLATFORM_UNKNOWN = "unknown";
    private static String PLATFORM_WINDOWS = "Windows";
    private static String PLATFORM_WINDOWS_CE = "Windows CE";
    private static String PLATFORM_APPLE = "Apple";
    private static String PLATFORM_LINUX = "Linux";
    private static String PLATFORM_OS2 = "OS/2";
    private static String PLATFORM_BEOS = "BeOS";
    private static String PLATFORM_IPHONE = "iPhone";
    private static String PLATFORM_IPOD = "iPod";
    private static String PLATFORM_IPAD = "iPad";
    private static String PLATFORM_BLACKBERRY = "BlackBerry";
    private static String PLATFORM_NOKIA = "Nokia";
    private static String PLATFORM_FREEBSD = "FreeBSD";
    private static String PLATFORM_OPENBSD = "OpenBSD";
    private static String PLATFORM_NETBSD = "NetBSD";
    private static String PLATFORM_SUNOS = "SunOS";
    private static String PLATFORM_OPENSOLARIS = "OpenSolaris";
    private static String PLATFORM_ANDROID = "Android";
    private static String OPERATING_SYSTEM_UNKNOWN = "unknown";

    public Browser(String $useragent) {
        this.reset();
        if (($useragent != null) && (!$useragent.isEmpty())) {
            this.setUserAgent($useragent);
        } else {
            this.determine();
        }
    }

    /**
     * Reset all properties
     */
    public void reset() {
        this.$_agent = "";
        this.$_browser_name = Browser.BROWSER_UNKNOWN;
        this.$_version = Browser.VERSION_UNKNOWN;
        this.$_platform = Browser.PLATFORM_UNKNOWN;
        this.$_os = Browser.OPERATING_SYSTEM_UNKNOWN;
        this.$_is_aol = false;
        this.$_is_mobile = false;
        this.$_is_robot = false;
        this.$_aol_version = Browser.VERSION_UNKNOWN;
    }

    /**
     * Check to see if the specific browser is valid
     *
     * @param string $browserName
     * @return True if the browser is the specified browser
     */
    public boolean isBrowser(String $browserName) {
        //return( 0 == strcasecmp(this.$_browser_name, trim($browserName)));
        return false;
    }

    /**
     * The name of the browser. All return types are from the class contants
     *
     * @return string Name of the browser
     */
    public String getBrowser() {
        return this.$_browser_name;
    }

    /**
     * Set the name of the browser
     *
     * @param $browser The name of the Browser
     */
    public void setBrowser(String $browser) {
        this.$_browser_name = $browser;
    }

    /**
     * The name of the platform. All return types are from the class contants
     *
     * @return string Name of the browser
     */
    public String getPlatform() {
        return this.$_platform;
    }

    /**
     * Set the name of the platform
     *
     * @param $platform The name of the Platform
     */
    public void setPlatform(String $platform) {
        this.$_platform = $platform;
    }

    /**
     * The version of the browser.
     *
     * @return string Version of the browser (will only contain alpha-numeric
     * characters and a period)
     */
    public String getVersion() {
        return this.$_version;
    }

    /**
     * Set the version of the browser
     *
     * @param $version The version of the Browser
     */
    public void setVersion(String $version) {
        //this.$_version = preg_replace('/[^0-9,.,a-z,A-Z-]/','',$version);
    }

    /**
     * The version of AOL.
     *
     * @return string Version of AOL (will only contain alpha-numeric characters
     * and a period)
     */
    public String getAolVersion() {
        return this.$_aol_version;
    }

    /**
     * Set the version of AOL
     *
     * @param $version The version of AOL
     */
    public void setAolVersion(String $version) {
        //this.$_aol_version = preg_replace('/[^0-9,.,a-z,A-Z]/','',$version);
    }

    /**
     * Is the browser from AOL?
     *
     * @return boolean True if the browser is from AOL otherwise false
     */
    public boolean isAol() {
        return this.$_is_aol;
    }

    /**
     * Is the browser from a mobile device?
     *
     * @return boolean True if the browser is from a mobile device otherwise
     * false
     */
    public boolean isMobile() {
        return this.$_is_mobile;
    }

    /**
     * Is the browser from a robot (ex Slurp,GoogleBot)?
     *
     * @return boolean True if the browser is from a robot otherwise false
     */
    public boolean isRobot() {
        return this.$_is_robot;
    }

    /**
     * Set the browser to be from AOL
     *
     * @param $isAol
     */
    public void setAol(boolean $isAol) {
        this.$_is_aol = $isAol;
    }

    /**
     * Set the Browser to be mobile
     *
     * @param boolean $value is the browser a mobile brower or not
     */
    protected void setMobile(boolean $value) {
        this.$_is_mobile = $value;
    }

    /**
     * Set the Browser to be mobile
     */
    protected void setMobile() {
        this.setMobile(true);
    }

    /**
     * Set the Browser to be a robot
     *
     * @param boolean $value is the browser a robot or not
     */
    protected void setRobot(boolean $value) {
        this.$_is_robot = $value;
    }

    /**
     * Set the Browser to be a robot
     */
    protected void setRobot() {
        this.setRobot(true);
    }

    /**
     * Get the user agent value in use to determine the browser
     *
     * @return string The user agent from the HTTP header
     */
    public String getUserAgent() {
        return this.$_agent;
    }

    /**
     * Set the user agent value (the construction will use the HTTP header value
     * - this will overwrite it)
     *
     * @param $agent_string The value for the User Agent
     */
    public void setUserAgent(String $agent_string) {
        this.reset();
        this.$_agent = $agent_string;
        this.determine();
    }

    /**
     * Used to determine if the browser is actually "chromeframe"
     *
     * @since 1.7
     * @return boolean True if the browser is using chromeframe
     */
    public boolean isChromeFrame() {
        //return( strpos(this.$_agent,"chromeframe") !== false );
        return false;
    }

    /**
     * Returns a formatted string with a summary of the details of the browser.
     *
     * @return string formatted string with a summary of the browser
     */
    public String __toString() {
        return "<strong>Browser Name:</strong>" + this.getBrowser() + "<br/>\n"
                + "<strong>Browser Version:</strong>{" + this.getVersion() + "<br/>\n"
                + "<strong>Browser User Agent String:</strong>" + this.getUserAgent() + "<br/>\n"
                + "<strong>Platform:</strong>" + this.getPlatform() + "<br/>";
    }

    /**
     * Protected routine to calculate and determine what the browser is in use
     * (including platform)
     */
    protected void determine() {
        this.checkPlatform();
        this.checkBrowsers();
        this.checkForAol();
    }

    /**
     * Protected routine to determine the browser type
     *
     * @return boolean True if the browser was detected otherwise false
     */
    protected boolean checkBrowsers() {
        return ( // well-known, well-used
                // Special Notes:
                // (1) Opera must be checked before FireFox due to the odd
                //     user agents used in some older versions of Opera
                // (2) WebTV is strapped onto Internet Explorer so we must
                //     check for WebTV before IE
                // (3) (deprecated) Galeon is based on Firefox and needs to be
                //     tested before Firefox is tested
                // (4) OmniWeb is based on Safari so OmniWeb check must occur
                //     before Safari
                // (5) Netscape 9+ is based on Firefox so Netscape checks
                //     before FireFox are necessary
                this.checkBrowserWebTv()
                || this.checkBrowserInternetExplorer()
                || this.checkBrowserOpera()
                || this.checkBrowserGaleon()
                || this.checkBrowserNetscapeNavigator9Plus()
                || this.checkBrowserFirefox()
                || this.checkBrowserChrome()
                || this.checkBrowserOmniWeb()
                || // common mobile
                this.checkBrowserAndroid()
                || this.checkBrowseriPad()
                || this.checkBrowseriPod()
                || this.checkBrowseriPhone()
                || this.checkBrowserBlackBerry()
                || this.checkBrowserNokia()
                || // common bots
                this.checkBrowserGoogleBot()
                || this.checkBrowserMSNBot()
                || this.checkBrowserSlurp()
                || // WebKit base check (post mobile and others)
                this.checkBrowserSafari()
                || // everyone else
                this.checkBrowserNetPositive()
                || this.checkBrowserFirebird()
                || this.checkBrowserKonqueror()
                || this.checkBrowserIcab()
                || this.checkBrowserPhoenix()
                || this.checkBrowserAmaya()
                || this.checkBrowserLynx()
                || this.checkBrowserShiretoko()
                || this.checkBrowserIceCat()
                || this.checkBrowserW3CValidator()
                || this.checkBrowserMozilla() /*
                 * Mozilla is such an open standard that you must check it last
                 */);
    }

    /**
     * Determine if the user is using a BlackBerry (last updated 1.7)
     *
     * @return boolean True if the browser is the BlackBerry browser otherwise
     * false
     */
    protected boolean checkBrowserBlackBerry() {
        /*
         * if ( stripos(this.$_agent,'blackberry') !== false ) { $aresult =
         * explode("/",stristr(this.$_agent,"BlackBerry")); $aversion =
         * explode(' ',$aresult[1]); this.$setVersion($aversion[0]);
         * this.$_browser_name = self::BROWSER_BLACKBERRY;
         * this.$setMobile(true); return true; }
         */
        return false;
    }

    /**
     * Determine if the user is using an AOL User Agent (last updated 1.7)
     *
     * @return boolean True if the browser is from AOL otherwise false
     */
    protected boolean checkForAol() {
        this.setAol(false);
        this.setAolVersion(Browser.VERSION_UNKNOWN);
        /*
         * if( stripos(this.$_agent,'aol') !== false ) { $aversion = explode('
         * ',stristr(this.$_agent, 'AOL')); this.$setAol(true);
         * this.$setAolVersion(preg_replace('/[^0-9\.a-z]/i', '',
         * $aversion[1])); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is the GoogleBot or not (last updated 1.7)
     *
     * @return boolean True if the browser is the GoogletBot otherwise false
     */
    protected boolean checkBrowserGoogleBot() {
        /*
         * if( stripos(this.$_agent,'googlebot') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'googlebot')); $aversion = explode('
         * ',$aresult[1]); this.$setVersion(str_replace(';','',$aversion[0]));
         * this.$_browser_name = self::BROWSER_GOOGLEBOT; this.$setRobot(true);
         * return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is the MSNBot or not (last updated 1.9)
     *
     * @return boolean True if the browser is the MSNBot otherwise false
     */
    protected boolean checkBrowserMSNBot() {
        /*
         * if( stripos(this.$_agent,"msnbot") !== false ) { $aresult =
         * explode("/",stristr(this.$_agent,"msnbot")); $aversion = explode("
         * ",$aresult[1]); this.$setVersion(str_replace(";","",$aversion[0]));
         * this.$_browser_name = self::BROWSER_MSNBOT; this.$setRobot(true);
         * return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is the W3C Validator or not (last updated 1.7)
     *
     * @return boolean True if the browser is the W3C Validator otherwise false
     */
    protected boolean checkBrowserW3CValidator() {
        /*
         * if( stripos(this.$_agent,'W3C-checklink') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'W3C-checklink')); $aversion =
         * explode(' ',$aresult[1]); this.$setVersion($aversion[0]);
         * this.$_browser_name = self::BROWSER_W3CVALIDATOR; return true; } else
         * if( stripos(this.$_agent,'W3C_Validator') !== false ) { // Some of
         * the Validator versions do not delineate w/ a slash - add it back in
         * $ua = str_replace("W3C_Validator ", "W3C_Validator/", this.$_agent);
         * $aresult = explode('/',stristr($ua,'W3C_Validator')); $aversion =
         * explode(' ',$aresult[1]); this.$setVersion($aversion[0]);
         * this.$_browser_name = self::BROWSER_W3CVALIDATOR; return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is the Yahoo! Slurp Robot or not (last updated
     * 1.7)
     *
     * @return boolean True if the browser is the Yahoo! Slurp Robot otherwise
     * false
     */
    protected boolean checkBrowserSlurp() {
        /*
         * if( stripos(this.$_agent,'slurp') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'Slurp')); $aversion = explode('
         * ',$aresult[1]); this.$setVersion($aversion[0]); this.$_browser_name =
         * self::BROWSER_SLURP; this.$setRobot(true); this.$setMobile(false);
         * return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Internet Explorer or not (last updated 1.7)
     *
     * @return boolean True if the browser is Internet Explorer otherwise false
     */
    protected boolean checkBrowserInternetExplorer() {
        /*
         * // Test for v1 - v1.5 IE if( stripos(this.$_agent,'microsoft
         * internet explorer') !== false ) { this.$setBrowser(self::BROWSER_IE);
         * this.$setVersion('1.0'); $aresult = stristr(this.$_agent, '/'); if(
         * preg_match('/308|425|426|474|0b1/i', $aresult) ) {
         * this.$setVersion('1.5'); } return true; } // Test for versions > 1.5
         * else if( stripos(this.$_agent,'msie') !== false &&
         * stripos(this.$_agent,'opera') === false ) { // See if the browser is
         * the odd MSN Explorer if( stripos(this.$_agent,'msnb') !== false ) {
         * $aresult = explode(' ',stristr(str_replace(';',';
         * ',this.$_agent),'MSN')); this.$setBrowser( self::BROWSER_MSN );
         * this.$setVersion(str_replace(array('(',')',';'),'',$aresult[1]));
         * return true; } $aresult = explode(' ',stristr(str_replace(';',';
         * ',this.$_agent),'msie')); this.$setBrowser( self::BROWSER_IE );
         * this.$setVersion(str_replace(array('(',')',';'),'',$aresult[1]));
         * return true; } // Test for Pocket IE else if(
         * stripos(this.$_agent,'mspie') !== false ||
         * stripos(this.$_agent,'pocket') !== false ) { $aresult = explode('
         * ',stristr(this.$_agent,'mspie')); this.$setPlatform(
         * self::PLATFORM_WINDOWS_CE ); this.$setBrowser(
         * self::BROWSER_POCKET_IE ); this.$setMobile(true);
         *
         * if( stripos(this.$_agent,'mspie') !== false ) {
         * this.$setVersion($aresult[1]); } else { $aversion =
         * explode('/',this.$_agent); this.$setVersion($aversion[1]); } return
         * true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Opera or not (last updated 1.7)
     *
     * @return boolean True if the browser is Opera otherwise false
     */
    protected boolean checkBrowserOpera() {
        /*
         * if( stripos(this.$_agent,'opera mini') !== false ) { $resultant =
         * stristr(this.$_agent, 'opera mini'); if(
         * preg_match('/\//',$resultant) ) { $aresult = explode('/',$resultant);
         * $aversion = explode(' ',$aresult[1]); this.$setVersion($aversion[0]);
         * } else { $aversion = explode(' ',stristr($resultant,'opera mini'));
         * this.$setVersion($aversion[1]); } this.$_browser_name =
         * self::BROWSER_OPERA_MINI; this.$setMobile(true); return true; } else
         * if( stripos(this.$_agent,'opera') !== false ) { $resultant =
         * stristr(this.$_agent, 'opera'); if(
         * preg_match('/Version\/(10.*)$/',$resultant,$matches) ) {
         * this.$setVersion($matches[1]); } else if(
         * preg_match('/\//',$resultant) ) { $aresult =
         * explode('/',str_replace("("," ",$resultant)); $aversion = explode('
         * ',$aresult[1]); this.$setVersion($aversion[0]); } else { $aversion =
         * explode(' ',stristr($resultant,'opera'));
         * this.$setVersion(isset($aversion[1])?$aversion[1]:""); }
         * this.$_browser_name = self::BROWSER_OPERA; return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Chrome or not (last updated 1.7)
     *
     * @return boolean True if the browser is Chrome otherwise false
     */
    protected boolean checkBrowserChrome() {
        /*
         * if( stripos(this.$_agent,'Chrome') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'Chrome')); $aversion = explode('
         * ',$aresult[1]); this.$setVersion($aversion[0]);
         * this.$setBrowser(self::BROWSER_CHROME); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is WebTv or not (last updated 1.7)
     *
     * @return boolean True if the browser is WebTv otherwise false
     */
    protected boolean checkBrowserWebTv() {
        /*
         * if( stripos(this.$_agent,'webtv') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'webtv')); $aversion = explode('
         * ',$aresult[1]); this.$setVersion($aversion[0]);
         * this.$setBrowser(self::BROWSER_WEBTV); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is NetPositive or not (last updated 1.7)
     *
     * @return boolean True if the browser is NetPositive otherwise false
     */
    protected boolean checkBrowserNetPositive() {
        /*
         * if( stripos(this.$_agent,'NetPositive') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'NetPositive')); $aversion =
         * explode(' ',$aresult[1]);
         * this.$setVersion(str_replace(array('(',')',';'),'',$aversion[0]));
         * this.$setBrowser(self::BROWSER_NETPOSITIVE); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Galeon or not (last updated 1.7)
     *
     * @return boolean True if the browser is Galeon otherwise false
     */
    protected boolean checkBrowserGaleon() {
        /*
         * if( stripos(this.$_agent,'galeon') !== false ) { $aresult = explode('
         * ',stristr(this.$_agent,'galeon')); $aversion =
         * explode('/',$aresult[0]); this.$setVersion($aversion[1]);
         * this.$setBrowser(self::BROWSER_GALEON); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Konqueror or not (last updated 1.7)
     *
     * @return boolean True if the browser is Konqueror otherwise false
     */
    protected boolean checkBrowserKonqueror() {
        /*
         * if( stripos(this.$_agent,'Konqueror') !== false ) { $aresult =
         * explode(' ',stristr(this.$_agent,'Konqueror')); $aversion =
         * explode('/',$aresult[0]); this.$setVersion($aversion[1]);
         * this.$setBrowser(self::BROWSER_KONQUEROR); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is iCab or not (last updated 1.7)
     *
     * @return boolean True if the browser is iCab otherwise false
     */
    protected boolean checkBrowserIcab() {
        /*
         * if( stripos(this.$_agent,'icab') !== false ) { $aversion = explode('
         * ',stristr(str_replace('/',' ',this.$_agent),'icab'));
         * this.$setVersion($aversion[1]); this.$setBrowser(self::BROWSER_ICAB);
         * return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is OmniWeb or not (last updated 1.7)
     *
     * @return boolean True if the browser is OmniWeb otherwise false
     */
    protected boolean checkBrowserOmniWeb() {
        /*
         * if( stripos(this.$_agent,'omniweb') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'omniweb')); $aversion = explode('
         * ',isset($aresult[1])?$aresult[1]:""); this.$setVersion($aversion[0]);
         * this.$setBrowser(self::BROWSER_OMNIWEB); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Phoenix or not (last updated 1.7)
     *
     * @return boolean True if the browser is Phoenix otherwise false
     */
    protected boolean checkBrowserPhoenix() {
        /*
         * if( stripos(this.$_agent,'Phoenix') !== false ) { $aversion =
         * explode('/',stristr(this.$_agent,'Phoenix'));
         * this.$setVersion($aversion[1]);
         * this.$setBrowser(self::BROWSER_PHOENIX); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Firebird or not (last updated 1.7)
     *
     * @return boolean True if the browser is Firebird otherwise false
     */
    protected boolean checkBrowserFirebird() {
        /*
         * if( stripos(this.$_agent,'Firebird') !== false ) { $aversion =
         * explode('/',stristr(this.$_agent,'Firebird'));
         * this.$setVersion($aversion[1]);
         * this.$setBrowser(self::BROWSER_FIREBIRD); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Netscape Navigator 9+ or not (last updated
     * 1.7) NOTE: (http://browser.netscape.com/ - Official support ended on
     * March 1st, 2008)
     *
     * @return boolean True if the browser is Netscape Navigator 9+ otherwise
     * false
     */
    protected boolean checkBrowserNetscapeNavigator9Plus() {
        /*
         * if( stripos(this.$_agent,'Firefox') !== false &&
         * preg_match('/Navigator\/([^ ]*)/i',this.$_agent,$matches) ) {
         * this.$setVersion($matches[1]);
         * this.$setBrowser(self::BROWSER_NETSCAPE_NAVIGATOR); return true; }
         * else if( stripos(this.$_agent,'Firefox') === false &&
         * preg_match('/Netscape6?\/([^ ]*)/i',this.$_agent,$matches) ) {
         * this.$setVersion($matches[1]);
         * this.$setBrowser(self::BROWSER_NETSCAPE_NAVIGATOR); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Shiretoko or not
     * (https://wiki.mozilla.org/Projects/shiretoko) (last updated 1.7)
     *
     * @return boolean True if the browser is Shiretoko otherwise false
     */
    protected boolean checkBrowserShiretoko() {
        /*
         * if( stripos(this.$_agent,'Mozilla') !== false &&
         * preg_match('/Shiretoko\/([^ ]*)/i',this.$_agent,$matches) ) {
         * this.$setVersion($matches[1]);
         * this.$setBrowser(self::BROWSER_SHIRETOKO); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Ice Cat or not
     * (http://en.wikipedia.org/wiki/GNU_IceCat) (last updated 1.7)
     *
     * @return boolean True if the browser is Ice Cat otherwise false
     */
    protected boolean checkBrowserIceCat() {
        /*
         * if( stripos(this.$_agent,'Mozilla') !== false &&
         * preg_match('/IceCat\/([^ ]*)/i',this.$_agent,$matches) ) {
         * this.$setVersion($matches[1]);
         * this.$setBrowser(self::BROWSER_ICECAT); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Nokia or not (last updated 1.7)
     *
     * @return boolean True if the browser is Nokia otherwise false
     */
    protected boolean checkBrowserNokia() {
        /*
         * if( preg_match("/Nokia([^\/]+)\/([^ SP]+)/i",this.$_agent,$matches) )
         * { this.$setVersion($matches[2]); if( stripos(this.$_agent,'Series60')
         * !== false || strpos(this.$_agent,'S60') !== false ) {
         * this.$setBrowser(self::BROWSER_NOKIA_S60); } else { this.$setBrowser(
         * self::BROWSER_NOKIA ); } this.$setMobile(true); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Firefox or not (last updated 1.7)
     *
     * @return boolean True if the browser is Firefox otherwise false
     */
    protected boolean checkBrowserFirefox() {
        /*
         * if( stripos(this.$_agent,'safari') === false ) { if(
         * preg_match("/Firefox[\/ \(]([^ ;\)]+)/i",this.$_agent,$matches) ) {
         * this.$setVersion($matches[1]);
         * this.$setBrowser(self::BROWSER_FIREFOX); return true; } else if(
         * preg_match("/Firefox$/i",this.$_agent,$matches) ) {
         * this.$setVersion(""); this.$setBrowser(self::BROWSER_FIREFOX); return
         * true; } }
         */
        return false;
    }

    /**
     * Determine if the browser is Firefox or not (last updated 1.7)
     *
     * @return boolean True if the browser is Firefox otherwise false
     */
    protected boolean checkBrowserIceweasel() {
        /*
         * if( stripos(this.$_agent,'Iceweasel') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'Iceweasel')); $aversion = explode('
         * ',$aresult[1]); this.$setVersion($aversion[0]);
         * this.$setBrowser(self::BROWSER_ICEWEASEL); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Mozilla or not (last updated 1.7)
     *
     * @return boolean True if the browser is Mozilla otherwise false
     */
    protected boolean checkBrowserMozilla() {
        /*
         * if( stripos(this.$_agent,'mozilla') !== false &&
         * preg_match('/rv:[0-9].[0-9][a-b]?/i',this.$_agent) &&
         * stripos(this.$_agent,'netscape') === false) { $aversion = explode('
         * ',stristr(this.$_agent,'rv:'));
         * preg_match('/rv:[0-9].[0-9][a-b]?/i',this.$_agent,$aversion);
         * this.$setVersion(str_replace('rv:','',$aversion[0]));
         * this.$setBrowser(self::BROWSER_MOZILLA); return true; } else if(
         * stripos(this.$_agent,'mozilla') !== false &&
         * preg_match('/rv:[0-9]\.[0-9]/i',this.$_agent) &&
         * stripos(this.$_agent,'netscape') === false ) { $aversion =
         * explode('',stristr(this.$_agent,'rv:'));
         * this.$setVersion(str_replace('rv:','',$aversion[0]));
         * this.$setBrowser(self::BROWSER_MOZILLA); return true; } else if(
         * stripos(this.$_agent,'mozilla') !== false &&
         * preg_match('/mozilla\/([^ ]*)/i',this.$_agent,$matches) &&
         * stripos(this.$_agent,'netscape') === false ) {
         * this.$setVersion($matches[1]);
         * this.$setBrowser(self::BROWSER_MOZILLA); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Lynx or not (last updated 1.7)
     *
     * @return boolean True if the browser is Lynx otherwise false
     */
    protected boolean checkBrowserLynx() {
        /*
         * if( stripos(this.$_agent,'lynx') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'Lynx')); $aversion = explode('
         * ',(isset($aresult[1])?$aresult[1]:""));
         * this.$setVersion($aversion[0]); this.$setBrowser(self::BROWSER_LYNX);
         * return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Amaya or not (last updated 1.7)
     *
     * @return boolean True if the browser is Amaya otherwise false
     */
    protected boolean checkBrowserAmaya() {
        /*
         * if( stripos(this.$_agent,'amaya') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'Amaya')); $aversion = explode('
         * ',$aresult[1]); this.$setVersion($aversion[0]);
         * this.$setBrowser(self::BROWSER_AMAYA); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Safari or not (last updated 1.7)
     *
     * @return boolean True if the browser is Safari otherwise false
     */
    protected boolean checkBrowserSafari() {
        /*
         * if( stripos(this.$_agent,'Safari') !== false &&
         * stripos(this.$_agent,'iPhone') === false &&
         * stripos(this.$_agent,'iPod') === false ) { $aresult =
         * explode('/',stristr(this.$_agent,'Version')); if( isset($aresult[1])
         * ) { $aversion = explode(' ',$aresult[1]);
         * this.$setVersion($aversion[0]); } else {
         * this.$setVersion(self::VERSION_UNKNOWN); }
         * this.$setBrowser(self::BROWSER_SAFARI); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is iPhone or not (last updated 1.7)
     *
     * @return boolean True if the browser is iPhone otherwise false
     */
    protected boolean checkBrowseriPhone() {
        /*
         * if( stripos(this.$_agent,'iPhone') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'Version')); if( isset($aresult[1])
         * ) { $aversion = explode(' ',$aresult[1]);
         * this.$setVersion($aversion[0]); } else {
         * this.$setVersion(self::VERSION_UNKNOWN); } this.$setMobile(true);
         * this.$setBrowser(self::BROWSER_IPHONE); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is iPod or not (last updated 1.7)
     *
     * @return boolean True if the browser is iPod otherwise false
     */
    protected boolean checkBrowseriPad() {
        /*
         * if( stripos(this.$_agent,'iPad') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'Version')); if( isset($aresult[1])
         * ) { $aversion = explode(' ',$aresult[1]);
         * this.$setVersion($aversion[0]); } else {
         * this.$setVersion(self::VERSION_UNKNOWN); } this.$setMobile(true);
         * this.$setBrowser(self::BROWSER_IPAD); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is iPod or not (last updated 1.7)
     *
     * @return boolean True if the browser is iPod otherwise false
     */
    protected boolean checkBrowseriPod() {
        /*
         * if( stripos(this.$_agent,'iPod') !== false ) { $aresult =
         * explode('/',stristr(this.$_agent,'Version')); if( isset($aresult[1])
         * ) { $aversion = explode(' ',$aresult[1]);
         * this.$setVersion($aversion[0]); } else {
         * this.$setVersion(self::VERSION_UNKNOWN); } this.$setMobile(true);
         * this.$setBrowser(self::BROWSER_IPOD); return true; }
         */
        return false;
    }

    /**
     * Determine if the browser is Android or not (last updated 1.7)
     *
     * @return boolean True if the browser is Android otherwise false
     */
    protected boolean checkBrowserAndroid() {
        /*
         * if( stripos(this.$_agent,'Android') !== false ) { $aresult =
         * explode(' ',stristr(this.$_agent,'Android')); if( isset($aresult[1])
         * ) { $aversion = explode(' ',$aresult[1]);
         * this.$setVersion($aversion[0]); } else {
         * this.$setVersion(self::VERSION_UNKNOWN); } this.$setMobile(true);
         * this.$setBrowser(self::BROWSER_ANDROID); return true; }
         */
        return false;
    }

    /**
     * Determine the user's platform (last updated 1.7)
     */
    protected void checkPlatform() {
        if (Text.hasString(this.$_agent, "windows")) {
            this.$_platform = Browser.PLATFORM_WINDOWS;
        }

        /*
         * else if( stripos(this.$_agent, 'iPad') !== false ) { this.$_platform
         * = self::PLATFORM_IPAD; } else if( stripos(this.$_agent, 'iPod') !==
         * false ) { this.$_platform = self::PLATFORM_IPOD; } else if(
         * stripos(this.$_agent, 'iPhone') !== false ) { this.$_platform =
         * self::PLATFORM_IPHONE; } elseif( stripos(this.$_agent, 'mac') !==
         * false ) { this.$_platform = self::PLATFORM_APPLE; } elseif(
         * stripos(this.$_agent, 'android') !== false ) { this.$_platform =
         * self::PLATFORM_ANDROID; } elseif( stripos(this.$_agent, 'linux') !==
         * false ) { this.$_platform = self::PLATFORM_LINUX; } else if(
         * stripos(this.$_agent, 'Nokia') !== false ) { this.$_platform =
         * self::PLATFORM_NOKIA; } else if( stripos(this.$_agent, 'BlackBerry')
         * !== false ) { this.$_platform = self::PLATFORM_BLACKBERRY; } elseif(
         * stripos(this.$_agent,'FreeBSD') !== false ) { this.$_platform =
         * self::PLATFORM_FREEBSD; } elseif( stripos(this.$_agent,'OpenBSD') !==
         * false ) { this.$_platform = self::PLATFORM_OPENBSD; } elseif(
         * stripos(this.$_agent,'NetBSD') !== false ) { this.$_platform =
         * self::PLATFORM_NETBSD; } elseif( stripos(this.$_agent, 'OpenSolaris')
         * !== false ) { this.$_platform = self::PLATFORM_OPENSOLARIS; } elseif(
         * stripos(this.$_agent, 'SunOS') !== false ) { this.$_platform =
         * self::PLATFORM_SUNOS; } elseif( stripos(this.$_agent, 'OS\/2') !==
         * false ) { this.$_platform = self::PLATFORM_OS2; } elseif(
         * stripos(this.$_agent, 'BeOS') !== false ) { this.$_platform =
         * self::PLATFORM_BEOS; } elseif( stripos(this.$_agent, 'win') !== false
         * ) { this.$_platform = self::PLATFORM_WINDOWS; }
         */
    }
}