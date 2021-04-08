package me.aggellos2001.ksurvivaleuplugin.utils

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL


/**
 * Created by DefianceCoding on 12/21/2017. Updated 7/20/18 to /v2 API
 */
class PCDetection {
    var status: String? = null
    var node: String? = null
    var ip: String? = null
    var asn: String? = null
    var provider: String? = null
    var country: String? = null
    var isocode: String? = null
    var proxy: String? = null
    var type: String? = null
    var port: String? = null
    var last_seen_human: String? = null
    var last_seen_unix: String? = null
    var query_time: String? = null
    var message: String? = null
    var error: String? = null
    var risk = 0

    /**
     * Allows you to lookup and determine info of a specified IPv4 or IPv6 Address and check
     * if it belongs to a VPN Network, Proxy Server, Tor Network, and or Mobile Network
     *
     *
     *
     *
     * This class facilitates and simplifies using the web API, and allows you to
     * easily implement the functionality in your applications.
     *
     *
     * API Homepage: https://ProxyCheck.io
     *
     * @author DefianceCoding
     */
    var api_key: String
    private var api_url = "http://proxycheck.io/v2/"
    private var api_timeout = 5000
    private var useVpn = 0
    private var useAsn = 0
    private var useNode = 0
    private var useTime = 0
    private var useInf = 0
    private var usePort = 0
    private var useSeen = 0
    private var useRisk = 0
    private var useDays = 7
    private var tag: String? = null

    constructor(key: String) {
        api_key = key
    }

    constructor(key: String, timeout: Int) {
        api_key = key
        api_timeout = timeout
    }

    /**
     * Here is where you can set your API Key from your user dashboard
     *
     * @param key APIKey from RESTful API
     */
    fun set_api_key(key: String) {
        api_key = key
    }

    /**
     * When the vpn flag is supplied we will perform a VPN check on the IP Address and present the result to you.
     *
     * @param variable Boolean value to set if you will check for VPN's
     */
    fun setUseVpn(variable: Boolean) {
        if (variable) {
            useVpn = 1
        } else {
            useVpn = 0
        }
    }

    /**
     * When the asn flag is supplied we will perform an ASN check on the IP Address and present you with the Provider name, ASN, Country and country isocode for the IP Address.
     *
     * @param variable Boolean value to set if you will check for the ASN
     */
    fun setUseAsn(variable: Boolean) {
        if (variable) {
            useAsn = 1
        } else {
            useAsn = 0
        }
    }

    /**
     * When the node flag is supplied we will display which node within our cluster answered your API call. This is only really needed for diagnosing problems with our support staff.
     *
     * @param variable Boolean value to set if you will check nodes
     */
    fun setUseNode(variable: Boolean) {
        if (variable) {
            useNode = 1
        } else {
            useNode = 0
        }
    }

    /**
     * When the time flag is supplied we will display how long this query took to be answered by our API excluding network overhead.
     *
     * @param variable Boolean value to set if you will check time it took to get results (note this doesn't count network overhead)
     */
    fun setUseTime(variable: Boolean) {
        if (variable) {
            useTime = 1
        } else {
            useTime = 0
        }
    }

    /**
     * When the inf flag is set to 0 (to disable it) we will not run this query through our real-time inference engine. In the absense of this flag or if it's set to 1 we will run the query through our real-time inference engine.
     *
     * @param variable Boolean value to set if you will check IPs in real time
     */
    fun setUseInf(variable: Boolean) {
        if (variable) {
            useInf = 1
        } else {
            useInf = 0
        }
    }

    /**
     * When the port flag is supplied we will display to you the port number we saw this IP Address operating a proxy server on.
     *
     * @param variable Boolean value to set if you will check Ports
     */
    fun setUsePort(variable: Boolean) {
        if (variable) {
            usePort = 1
        } else {
            usePort = 0
        }
    }

    /**
     * When the seen flag is supplied we will display to you the most recent time we saw this IP Address operating as a proxy server.
     *
     * @param variable Boolean value to set if you will check last seen used as a proxy
     */
    fun setUseSeen(variable: Boolean) {
        if (variable) {
            useSeen = 1
        } else {
            useSeen = 0
        }
    }

    /**
     * When the risk flag is set to 1 we will provide you with a risk score for this IP Address ranging from 0 to 100. Scores below 33 can be considered low risk while scores between 34 and 66 can be considered high risk. Addresses with scores above 66 are considered very dangerous.
     * <br></br>When the risk flag is set to 2 we will still provide you with the above risk score but you'll also receive individual counts for each type of attack we witnessed this IP performing across our customer network and our own honeypots within the days you specify by the &days= flag.
     * <br></br>When the risk flag is set to 0 (Default) it's not used.
     *
     * @param variable Int value to set
     */
    fun setUseRisk(variable: Int) {
        useRisk = variable
    }

    /**
     * When the days flag is supplied we will restrict our proxy results to between now and the amount of days you specify. For example if you supplied &days=2 we would only check our database for Proxies that we saw within the past 48 hours. By default without this flag supplied we search within the past 7 days.
     *
     * @param variable Number (int) value of how many days to go back in the database
     * this flag is used for if checking for newer or even older based proxies
     */
    fun setUseDays(variable: Int) {
        useDays = variable
    }

    /**
     * When the tag flag is supplied we will tag your query with the message you supply. You can supply your tag using the POST method and we recommend you do so.
     *
     * @param variable String that allows you to add a tag to users dashboards stating where the lookup came from
     */
    fun setTag(variable: String?) {
        tag = variable
    }

    /**
     * Units are measured in milliseconds and is the max time the API will try to poll info.
     *
     * @param timeout Time in Milliseconds the query will take before timing out
     */
    fun set_api_timeout(timeout: Int) {
        api_timeout = timeout
    }

    /**
     * Determines weather or not to use SSL when querying from the API Host
     */
    fun useSSL() {
        api_url = api_url.replace("http://", "https://")
    }

    /**
     * This method is used in filling the variables. This must be called at least once!
     *
     * @param ip IP to gather information on
     * @throws IOException
     * @throws ParseException
     */
    @Throws(IOException::class, ParseException::class)
    fun parseResults(ip: String) {
        val queryUrl = get_query_url(ip)
        val queryResult = query(queryUrl, api_timeout)
        val parser = JSONParser()
        val main = parser.parse(queryResult) as JSONObject
        val sub = main[ip] as JSONObject?
        status = main["status"] as String?
        query_time = main["query time"] as String?
        message = main["message"] as String?
        node = main["node"] as String?
        this.ip = ip
        //if error or denied then if we do not return here it will throw null pointer exception
        if (status.equals("error", ignoreCase = true) || status.equals("denied", ignoreCase = true)) {
            return
        }
        asn = sub?.get("asn") as String?
        provider = sub?.get("provider") as String?
        country = sub?.get("country") as String?
        isocode = sub?.get("isocode") as String?
        proxy = sub?.get("proxy") as String?
        type = sub?.get("type") as String?
        port = sub?.get("port") as String?
        last_seen_human = sub?.get("last seen human") as String?
        last_seen_unix = sub?.get("last seen unix") as String?
        error = sub?.get("error") as String?
        //without getOrDefault we get NPE for whitelisted/blacklisted IP's because risk value is not returned from API
        if (sub != null) {
            risk = (sub.getOrDefault("risk", 0L) as Long).toInt()
        }
    }

    /**
     * Gets full page response in String readable format normally will be used in debugging
     *
     * @param ip IP to query
     * @return String result
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getResponseAsString(ip: String): String {
        val query_url = get_query_url(ip)
        return query(query_url, api_timeout)
    }

    /**
     * Generates the URL used for Query based on settings above
     * fully you have fun
     *
     * @param ip IP To query
     * @return String URL
     */
    fun get_query_url(ip: String): String {
        return (api_url + ip + "?key=" + api_key
                + "&vpn=" + useVpn + "&asn=" + useAsn
                + "&node=" + useNode + "&time=" + useTime
                + "&inf=" + useInf + "&port=" + usePort
                + "&seen=" + useSeen + "&days=" + useDays
                + "&risk=" + useRisk + "&tag=" + tag)
    }

    /**
     * Function that will pull data from the API URL.
     *
     * @param urlParam full based url from arguments given
     * @param timeout timeout in milliseconds before api will give up
     * @return JSON Output of query
     * @throws MalformedURLException
     * @throws IOException
     */
    @Throws(MalformedURLException::class, IOException::class)
    fun query(urlParam: String?, timeout: Int): String {
        var url = urlParam
        val response = StringBuilder()
        val website = URL(url)
        val connection = website.openConnection()
        connection.connectTimeout = timeout
        connection.doInput = true
        connection.doOutput = true
        connection.setRequestProperty("User-Agent", "Defiance-AntiBot.v1.2.0")
        connection.setRequestProperty("tag", "Defiance-AntiBot.v1.2.0")
        BufferedReader(
            InputStreamReader(
                connection.getInputStream()
            )
        ).use { `in` ->
            while (`in`.readLine().also { url = it } != null) {
                response.append(url)
            }
        }
        return response.toString()
    }

    override fun toString(): String {
        return """
            r
        Status: $status 
        Node: $node 
        IP: $ip 
        Asn: $asn 
        Provider: $provider 
        Country: $country 
        IsoCode: $isocode 
        IsProxy: $proxy 
        Type: $type 
        Port: $port 
        Last seen human: $last_seen_human 
        Last seen unix: $last_seen_unix 
        Query Time: $query_time 
        API message: $message 
        Error (if any): $error 
        Risk factor: $risk""".trimIndent()
    }
}