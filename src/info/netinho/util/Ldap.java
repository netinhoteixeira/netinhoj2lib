package info.netinho.util;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author Francisco Ernesto Teixeira <fco.ernesto@gmail.com>
 */
public class Ldap {

    /*
     public static void main(String[] args) throws CommunicationException, NamingException, UnsupportedEncodingException {

     final String ldapAccountToLookup = "testetoken";

     LdapContext ctx = LdapUtil.getLdapContext(PropertiesUtil.getString("ldap.server"), PropertiesUtil.getString("ldap.username"), PropertiesUtil.getString("ldap.password"));
     //LdapUtil ldap = new LdapUtil();

     //1) lookup the ldap account
     SearchResult srLdapUser = LdapUtil.findAccountByAccountName(ctx, PropertiesUtil.getString("ldap.searchdomain"), ldapAccountToLookup);

     //2) get the SID of the users primary group
     String primaryGroupSID = LdapUtil.getPrimaryGroupSID(srLdapUser);

     //3) get the users Primary Group
     String primaryGroupName = LdapUtil.findGroupBySID(ctx, PropertiesUtil.getString("ldap.searchdomain"), primaryGroupSID);

     System.out.println(srLdapUser);
     System.out.println(primaryGroupSID);
     System.out.println(primaryGroupName);
     }
     */
    /**
     *
     * @param server
     * @param userName
     * @param password
     * @return
     * @throws NamingException
     */
    static public LdapContext getLdapContext(String server, String userName, String password) throws CommunicationException, NamingException, UnsupportedEncodingException {
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        //System.setProperty("javax.net.debug", "all");
        env.put(Context.SECURITY_AUTHENTICATION, "Simple");
        // NOTE: Replace the user and password in next two lines, this user should have privileges to change password.
        // NOTE: This is NOT the user whose password is being changed.
        env.put(Context.SECURITY_PRINCIPAL, userName);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // NOTE: Replace localhost in next line with actual ldap host:
        env.put(Context.PROVIDER_URL, server);
        env.put(Context.SECURITY_PROTOCOL, "ssl");

        // ensures that objectSID attribute values
        // will be returned as a byte[] instead of a String
        env.put("java.naming.ldap.attributes.binary", "objectSID");

        // the following is helpful in debugging errors
        env.put("com.sun.jndi.ldap.trace.ber", System.err);

        // Create the initial directory context
        LdapContext ctx = new InitialLdapContext(env, null);

        return ctx;
    }

    /*
     Data codes related to 'LDAP: error code 49' with Microsoft Active Directory

     Technote (troubleshooting)

     Problem
     When IBM® WebSphere® Portal accesses the LDAP (in this case Microsoft® Active Directory), either to start the server or during configuration tasks, "LDAP: error code 49" can be encountered.
     Symptom
     Generally, error references SECJ0369E and SECJ0055E will be generated in the SystemOut.log. There are, however, various root causes that can be derived from the values that follow the initial description. An example is shown below.
     From SystemOut.log:

     [date/time] 0000000a LdapRegistryI A SECJ0419I: The user registry is currently connected to the LDAP server ldap://<hostname>:389.
     [date/time] 0000000a LTPAServerObj E SECJ0369E: Authentication failed when using LTPA. The exception is [LDAP: error code 49 - 80090308: LdapErr: DSID-0C090334, comment: AcceptSecurityContext error, data 775, vece ].
     [date/time] 0000000a distContextMa E SECJ0270E: Failed to get actual credentials. The exception is javax.naming.AuthenticationException: [LDAP: error code 49 - 80090308: LdapErr: DSID-0C090334, comment: AcceptSecurityContext error, data 775, vece ]
     at com.sun.jndi.ldap.LdapCtx.mapErrorCode(LdapCtx.java:3005)

     In this case, validate-ldap is the config task that was failing, and from the ConfigTrace.log we see:

     action-validate-ldap-was-admin-user:
     [ldapcheck] ###########################
     [ldapcheck] ldapURL : <hostname>:389
     [ldapcheck] ldapUser : CN=wasadmin,OU=WebspherePortal,OU=Service Accounts,DC=select,DC=corp,DC=sem
     [ldapcheck] ldapPassword : *********
     [ldapcheck] ldapSslEnabled : false
     [ldapcheck] javax.naming.AuthenticationException: [LDAP: error code 49 - 80090308: LdapErr: DSID-0C090334, comment: AcceptSecurityContext error, data 775, vece ]
     [ldapcheck] ERROR: 4
     [ldapcheck] Invalid or insufficient authorization privileges.
     Target finished: action-validate-ldap-was-admin-user
     Cause
     The error shown below is similar each time there is an LDAP authentication issue.
     "The exception is [ LDAP: error code 49 - 80090308: LdapErr: DSID-0Cxxxxxx, comment: AcceptSecurityContext error, data xxx, vece ]."

     However, there are several values that can indicate what LDAP function is causing the issue. Here are some general references for Microsoft Active Directory: 

     The AD-specific error code is the one after "data" and before "vece" or "v893" in the actual error string returned to the binding process 

     525	user not found
     52e	invalid credentials
     530	not permitted to logon at this time
     531	not permitted to logon at this workstation
     532	password expired
     533	account disabled
     701	account expired
     773	user must reset password
     775	user account locked
      
     Common Active Directory LDAP bind errors: 

     80090308: LdapErr: DSID-0C09030B, comment: AcceptSecurityContext error, data 525, v893 
     HEX: 0x525 - user not found 
     DEC: 1317 - ERROR_NO_SUCH_USER (The specified account does not exist.) 
     NOTE: Returns when username is invalid. 

     80090308: LdapErr: DSID-0C09030B, comment: AcceptSecurityContext error, data 52e, v893 
     HEX: 0x52e - invalid credentials 
     DEC: 1326 - ERROR_LOGON_FAILURE (Logon failure: unknown user name or bad password.) 
     NOTE: Returns when username is valid but password/credential is invalid. Will prevent most other errors from being displayed as noted. 

     80090308: LdapErr: DSID-0C09030B, comment: AcceptSecurityContext error, data 530, v893 
     HEX: 0x530 - not permitted to logon at this time 
     DEC: 1328 - ERROR_INVALID_LOGON_HOURS (Logon failure: account logon time restriction violation.) 
     NOTE: Returns only when presented with valid username and password/credential. 

     80090308: LdapErr: DSID-0C09030B, comment: AcceptSecurityContext error, data 531, v893 
     HEX: 0x531 - not permitted to logon from this workstation 
     DEC: 1329 - ERROR_INVALID_WORKSTATION (Logon failure: user not allowed to log on to this computer.) 
     LDAP[userWorkstations: <multivalued list of workstation names>] 
     NOTE: Returns only when presented with valid username and password/credential. 

     80090308: LdapErr: DSID-0C09030B, comment: AcceptSecurityContext error, data 532, v893 
     HEX: 0x532 - password expired 
     DEC: 1330 - ERROR_PASSWORD_EXPIRED (Logon failure: the specified account password has expired.) 
     LDAP[userAccountControl: <bitmask=0x00800000>] - PASSWORDEXPIRED 
     NOTE: Returns only when presented with valid username and password/credential. 

     80090308: LdapErr: DSID-0C09030B, comment: AcceptSecurityContext error, data 533, v893 
     HEX: 0x533 - account disabled 
     DEC: 1331 - ERROR_ACCOUNT_DISABLED (Logon failure: account currently disabled.) 
     LDAP[userAccountControl: <bitmask=0x00000002>] - ACCOUNTDISABLE 
     NOTE: Returns only when presented with valid username and password/credential. 

     80090308: LdapErr: DSID-0C09030B, comment: AcceptSecurityContext error, data 701, v893 
     HEX: 0x701 - account expired 
     DEC: 1793 - ERROR_ACCOUNT_EXPIRED (The user's account has expired.) 
     LDAP[accountExpires: <value of -1, 0, or extemely large value indicates account will not expire>] - ACCOUNTEXPIRED 
     NOTE: Returns only when presented with valid username and password/credential. 

     80090308: LdapErr: DSID-0C09030B, comment: AcceptSecurityContext error, data 773, v893 
     HEX: 0x773 - user must reset password 
     DEC: 1907 - ERROR_PASSWORD_MUST_CHANGE (The user's password must be changed before logging on the first time.) 
     LDAP[pwdLastSet: <value of 0 indicates admin-required password change>] - MUST_CHANGE_PASSWD 
     NOTE: Returns only when presented with valid username and password/credential. 

     80090308: LdapErr: DSID-0C09030B, comment: AcceptSecurityContext error, data 775, v893 
     HEX: 0x775 - account locked out 
     DEC: 1909 - ERROR_ACCOUNT_LOCKED_OUT (The referenced account is currently locked out and may not be logged on to.) 
     LDAP[userAccountControl: <bitmask=0x00000010>] - LOCKOUT 
     NOTE: Returns even if invalid password is presented 

     The DEC: values are not presented in Portal logs; however, review of LDAP activity combined with analysis of SystemOut.log and relevant configuration tasks can help narrow down the root cause.

     Resolving the problem
     Use the codes above to verify the settings and users in LDAP.
     */
    static public String decodeMessage(String message) {
        ////if (message.indexOf("error code 49") > -1) {
        if (message.indexOf("data 525") > -1) {
            // 525 - user not found
            message = "Usuário não encontrado.";
        } else if (message.indexOf("data 52e") > -1) {
            // 52e - invalid credentials
            message = "Credenciais inválidas.";
        } else if (message.indexOf("data 530") > -1) {
            // 530 - not permitted to logon at this time
            message = "Acesso não autorizado temporariamente.";
        } else if (message.indexOf("data 531") > -1) {
            // 531 - not permitted to logon at this workstation
            message = "Acesso não autorizado deste terminal.";
        } else if (message.indexOf("data 532") > -1) {
            // 532 - password expired
            message = "A senha expirou.";
        } else if (message.indexOf("data 533") > -1) {
            // 533 - account disabled
            message = "Conta desabilitada.";
        } else if (message.indexOf("data 701") > -1) {
            // 701 - account expired
            message = "A conta expirou.";
        } else if (message.indexOf("data 773") > -1) {
            // 773 - user must reset password
            message = "O usuário precisa pedir uma nova senha.";
        } else if (message.indexOf("data 775") > -1) {
            // 775 - user account locked
            message = "Usuário bloqueado.";
        } else if (message.indexOf("error code 53 - 00002077") > -1) {
            // error code 53 - 00002077 (8311L) - ERROR_DS_ILLEGAL_MOD_OPERATION: Illegal modify operation. Some aspect of the modification is not permitted.
            message = "Tentativa de modificação não autorizada. Algum aspecto da modificação não é permitida.";
        } else if (message.indexOf("error code 53 - 0000052D") > -1) {
            // error code 53 - 0000052D () - SvcErr: DSID-031A120C, problem 5003 (WILL_NOT_PERFORM), data 0
            message = "A senha não foi alterada. Não atende os requisitos mínimos de segurança ou já foi utilizada.";
        } else if (message.indexOf("error code 50 - 00000005") > -1) {
            // error code 50 - 00000005: SecErr: DSID-031A1190, problem 4003 (INSUFF_ACCESS_RIGHTS), data 0
            message = "O usuário não tem direitos de acesso suficientes.";
        }

        /////}
        return message;
    }

    /**
     *
     * @param ctx
     * @param ldapSearchBase
     * @param accountName
     * @return
     * @throws NamingException
     */
    static public SearchResult findAccountByAccountName(DirContext ctx, String ldapSearchBase, String accountName) throws CommunicationException, NamingException {

        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

        SearchResult searchResult = null;
        if (results.hasMoreElements()) {
            searchResult = (SearchResult) results.nextElement();

            //make sure there is not another item available, there should be only 1 match
            if (results.hasMoreElements()) {
                System.err.println("Matched multiple users for the accountName: " + accountName);
                return null;
            }
        }

        return searchResult;
    }

    /**
     *
     * @param ctx
     * @param ldapSearchBase
     * @param sid
     * @return
     * @throws NamingException
     */
    static public String findGroupBySID(DirContext ctx, String ldapSearchBase, String sid) throws CommunicationException, NamingException {

        String searchFilter = "(&(objectClass=group)(objectSid=" + sid + "))";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

        if (results.hasMoreElements()) {
            SearchResult searchResult = (SearchResult) results.nextElement();

            // make sure there is not another item available, there should be only 1 match
            if (results.hasMoreElements()) {
                System.err.println("Matched multiple groups for the group with SID: " + sid);
                return null;
            } else {
                return (String) searchResult.getAttributes().get("sAMAccountName").get();
            }
        }
        return null;
    }

    /**
     *
     * @param srLdapUser
     * @return
     * @throws NamingException
     */
    static public String getPrimaryGroupSID(SearchResult srLdapUser) throws CommunicationException, NamingException {
        byte[] objectSID = (byte[]) srLdapUser.getAttributes().get("objectSid").get();
        String strPrimaryGroupID = (String) srLdapUser.getAttributes().get("primaryGroupID").get();

        String strObjectSid = decodeSID(objectSID);

        return strObjectSid.substring(0, strObjectSid.lastIndexOf('-') + 1) + strPrimaryGroupID;
    }

    /**
     * The binary data is in the form: byte[0] - revision level byte[1] - count
     * of sub-authorities byte[2-7] - 48 bit authority (big-endian) and then
     * count x 32 bit sub authorities (little-endian)
     *
     * The String value is: S-Revision-Authority-SubAuthority[n]...
     *
     * Based on code from here -
     * http://forums.oracle.com/forums/thread.jspa?threadID=1155740&tstart=0
     */
    static public String decodeSID(byte[] sid) {

        final StringBuilder strSid = new StringBuilder("S-");

        // get version
        final int revision = sid[0];
        strSid.append(Integer.toString(revision));

        //next byte is the count of sub-authorities
        final int countSubAuths = sid[1] & 0xFF;

        //get the authority
        long authority = 0;
        //String rid = "";
        for (int i = 2; i <= 7; i++) {
            authority |= ((long) sid[i]) << (8 * (5 - (i - 2)));
        }
        strSid.append("-");
        strSid.append(Long.toHexString(authority));

        //iterate all the sub-auths
        int offset = 8;
        int size = 4; //4 bytes for each sub auth
        for (int j = 0; j < countSubAuths; j++) {
            long subAuthority = 0;
            for (int k = 0; k < size; k++) {
                subAuthority |= (long) (sid[offset + k] & 0xFF) << (8 * k);
            }

            strSid.append("-");
            strSid.append(subAuthority);

            offset += size;
        }

        return strSid.toString();
    }

    /**
     *
     * @param connection
     * @param userName
     * @return
     * @throws NamingException
     */
    static public String getEmail(LdapContext ctx, String searchDomain, String userName) throws CommunicationException, NamingException {
        SearchResult srLdapUser = Ldap.findAccountByAccountName(ctx, searchDomain, userName); // PropertiesUtil.getString("ldap.searchdomain")

        Attributes attrs = srLdapUser.getAttributes();

        return attrs.get("mail").toString();
    }

    /**
     *
     * @param ldapContext
     * @param username
     * @param password
     * @throws NamingException
     */
    static public void updatePassword(LdapContext ldapContext, String username, String password) throws CommunicationException, NamingException {
        String quotedPassword = "\"" + password + "\"";
        char unicodePwd[] = quotedPassword.toCharArray();
        byte pwdArray[] = new byte[unicodePwd.length * 2];
        for (int i = 0; i < unicodePwd.length; i++) {
            pwdArray[i * 2 + 1] = (byte) (unicodePwd[i] >>> 8);
            pwdArray[i * 2 + 0] = (byte) (unicodePwd[i] & 0xff);
        }

        ModificationItem[] mods = new ModificationItem[1];
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                new BasicAttribute("UnicodePwd", pwdArray));

        ldapContext.modifyAttributes(username, mods);
    }

    static public void replaceAttribute(LdapContext ldapContext, String username, String attribute, String value) throws CommunicationException, NamingException {
        ModificationItem[] mods = new ModificationItem[1];
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                new BasicAttribute(attribute, value));

        ldapContext.modifyAttributes(username, mods);
    }

    static public void addttribute(LdapContext ldapContext, String username, String attribute, String value) throws CommunicationException, NamingException {
        ModificationItem[] mods = new ModificationItem[1];
        mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
                new BasicAttribute(attribute, value));

        ldapContext.modifyAttributes(username, mods);
    }
}