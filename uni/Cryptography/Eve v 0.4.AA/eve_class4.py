import sys
import hashlib
import urllib3
from urllib3 import HTTPResponse
import urllib.robotparser
import certifi
import time
from random import choice
from random import randrange
from eve_util import EveUtil


class EveClass4:
    eveProtocol = ("http", "https")
    eveHost = "info.issauga.lt"
    eveAuth = ""
    eveAuthOpt = False
    evePort = "80"
    evePath = "/robots.txt"
    eveType = ("file", "manual")
    eveUA = "EveTheWanderer v0.4 eve-python v"
    eveSecret = "0WNAemWY5Hhi038Y1q1gkLw5L04BnG1iCwGanxAdHCkfKFliiwi7U010V32peTF5lhDW52SmhtXP6OCRSGf1dS4JP7cnIOF0u5Ey"
    eveProtocolOption = eveProtocol[0]
    eveTypeOption = eveType[0]
    filename = "eve_class4file.txt"

    def __init__(self, utility):
        self.util = utility
        self.eveOperationName = (
            self.get_translation(
                "t_crawler_for_server_status_and_header_information", "eve_class4"
            )
            + "."
        )
        self.eveOperationDescription = (
            self.get_translation("eve_class_four_description", "eve_class4") + "."
        )
        self.eveUA = (
            self.eveUA
            + str(sys.version_info.major)
            + "."
            + str(sys.version_info.minor)
            + "."
            + str(sys.version_info.micro)
        )
        self.eveAuth = hashlib.md5(bytes(self.eveHost + self.eveSecret, "utf-8"))
        self.http = urllib3.PoolManager(
            cert_reqs="CERT_REQUIRED", ca_certs=certifi.where()
        )
        self.fileContent = ""

    def get_translation(self, name, module):
        # get Translation
        return self.util.eveUtilRush(name, module)

    def notifications(self):
        if self.eveSecret == "":
            print(self.get_translation("t_attention", "eve_class4") + ":")
            print(
                "\t"
                + self.get_translation(
                    "t_secret_for_authentication_is_empty", "eve_class4"
                )
                + " ["
                + type(self).__name__
                + "]"
            )
            digits = [48, 57]
            upperc = [65, 90]
            lowerc = [97, 122]
            i = 0
            secret = ""
            while i != 100:
                dul = choice(["digits", "upperc", "lowerc"])
                if dul == "digits":
                    sc = randrange(digits[0], digits[1])
                elif dul == "upperc":
                    sc = randrange(upperc[0], upperc[1])
                elif dul == "lowerc":
                    sc = randrange(lowerc[0], lowerc[1])

                secret = str(secret) + chr(sc)
                i = i + 1
            print("\t" + self.get_translation("t_add_secret", "eve_class4") + ":")
            print("\t" + str(secret))

    def options(self):
        print(
            self.get_translation("t_loaded_operation", "eve_class4")
            + ": "
            + self.get_translation(
                "t_crawler_for_server_status_and_header_information", "eve_class4"
            )
        )
        print(self.get_translation("t_scan_options", "eve_class4") + ":")
        protocol = ""
        i = 1
        protocolLen = len(self.eveProtocol)
        for item in self.eveProtocol:
            protocol = protocol + item
            if protocolLen == i:
                protocol = protocol + "."
            else:
                protocol = protocol + ", "
            i += 1

        print("\t" + self.get_translation("t_ua", "eve_class4") + ":" + self.eveUA)
        print(
            "\t"
            + self.get_translation("t_all_available_protocols", "eve_class4")
            + ":"
            + protocol
        )
        print(
            "\t"
            + self.get_translation("t_protocol", "eve_class4")
            + ":"
            + self.eveProtocolOption
        )
        print(
            "\t"
            + self.get_translation("t_host", "eve_class4")
            + ":"
            + str(self.eveHost)
        )
        print(
            "\t"
            + self.get_translation("t_authentication", "eve_class4")
            + ":"
            + str(self.eveAuth.hexdigest())
        )
        print(
            "\t"
            + self.get_translation("t_auth_option", "eve_class4")
            + ":"
            + str(self.eveAuthOpt)
        )
        print(
            "\t"
            + self.get_translation("t_port", "eve_class4")
            + ":"
            + str(self.evePort)
        )
        print(
            "\t"
            + self.get_translation("t_path", "eve_class4")
            + ":"
            + str(self.evePath)
        )
        print(
            "\t"
            + self.get_translation("t_current_uri", "eve_class4")
            + ":"
            + str(self.eveProtocolOption)
            + "://"
            + str(self.eveHost)
            + ":"
            + str(self.evePort)
            + str(self.evePath)
        )

        type = ""
        i = 1
        typeLen = len(self.eveType)
        for item in self.eveType:
            type = type + item
            if typeLen == i:
                type = type + "."
            else:
                type = type + ", "
            i += 1

        print(
            "\t"
            + self.get_translation("t_all_available_types", "eve_class4")
            + ":"
            + type
        )
        print(
            "\t"
            + self.get_translation("t_type_prepared_to_launch", "eve_class4")
            + ":"
            + self.eveTypeOption
        )

    def eveSetOptions(self):
        print(
            "\t"
            + self.get_translation("t_current_protocol", "eve_class4")
            + ":"
            + str(self.eveProtocolOption)
        )
        if self.eveProtocolOption == self.eveProtocol[0]:
            value = input(
                "(1/6) "
                + self.get_translation("t_it_is_a_http_protocol", "eve_class4")
                + "? (y/n)"
            )
            if value == "y" or value == "Y":
                self.eveProtocolOption = self.eveProtocol[0]
            elif value == "n" or value == "N":
                self.eveProtocolOption = self.eveProtocol[1]
        else:
            value = input(
                "(1/6) "
                + self.get_translation("t_it_is_a_https_protocol", "eve_class4")
                + "? (y/n)"
            )
            if value == "y" or value == "Y":
                self.eveProtocolOption = self.eveProtocol[1]
            elif value == "n" or value == "N":
                self.eveProtocolOption = self.eveProtocol[0]
        print(
            "\t(1) "
            + self.get_translation("t_current_protocol", "eve_class4")
            + ":"
            + str(self.eveProtocolOption)
        )
        del value
        print(
            "\t"
            + self.get_translation("t_current_host", "eve_class4")
            + ":"
            + str(self.eveHost)
        )
        value = input(
            "(2/6) "
            + self.get_translation("t_specify_new_host_information", "eve_class4")
            + "? (y/n)"
        )
        if value == "y" or value == "Y":
            host = input(
                self.get_translation("t_enter_the_host_information", "eve_class4")
                + ":\n"
            )
            self.eveHost = str(host)  # TODO. Sec
            self.eveAuth = hashlib.md5(bytes(self.eveHost + self.eveSecret, "utf-8"))
        print(
            "\t(2) "
            + self.get_translation("t_current_host", "eve_class4")
            + ":"
            + str(self.eveHost)
        )
        del value
        print(
            "\t"
            + self.get_translation("t_current_port", "eve_class4")
            + ":"
            + str(self.evePort)
        )
        value = input(
            "(3/6) "
            + self.get_translation("t_specify_new_port", "eve_class4")
            + "? (y/n)"
        )
        if value == "y" or value == "Y":
            port = input(self.get_translation("t_enter_the_port", "eve_class4") + ":\n")
            self.evePort = int(port)
        print(
            "\t(3) "
            + self.get_translation("t_current_port", "eve_class4")
            + ":"
            + str(self.evePort)
        )
        del value
        print(
            "\t"
            + self.get_translation("t_current_path", "eve_class4")
            + ":"
            + str(self.evePath)
        )
        value = input(
            "(4/6) "
            + self.get_translation("t_specify_new_path_information", "eve_class4")
            + "? (y/n)"
        )
        if value == "y" or value == "Y":
            path = input(
                self.get_translation("t_enter_the_path_information", "eve_class4")
                + ":\n"
            )
            self.evePath = str(path)  # TODO. Sec
        print(
            "\t(4) "
            + self.get_translation("t_current_path", "eve_class4")
            + ":"
            + str(self.evePath)
        )
        del value
        print(
            "\t"
            + self.get_translation("t_current_scan_type", "eve_class4")
            + ":"
            + str(self.eveTypeOption)
        )
        if self.eveTypeOption == self.eveType[0]:
            value = input(
                "(5/6) "
                + self.get_translation(
                    "t_use_data_in_file_for_the_request", "eve_class4"
                )
                + "? (y/n)"
            )
            if value == "y" or value == "Y":
                self.eveTypeOption = self.eveType[0]
            elif value == "n" or value == "N":
                self.eveTypeOption = self.eveType[1]
        else:
            value = input(
                "(5/6) "
                + self.get_translation("t_it_is_a_manual_request", "eve_class4")
                + "? (y/n)"
            )
            if value == "y" or value == "Y":
                self.eveTypeOption = self.eveType[1]
            elif value == "n" or value == "N":
                self.eveTypeOption = self.eveType[0]
        print(
            "\t(5) "
            + self.get_translation("t_current_scan_type", "eve_class4")
            + ":"
            + str(self.eveTypeOption)
        )
        del value
        print(
            "\t"
            + self.get_translation("t_the_use_of_an_file_authentication", "eve_class4")
            + ":"
            + str(self.eveAuthOpt)
        )
        if self.eveAuthOpt == True:
            value = input(
                "(6/6) "
                + self.get_translation(
                    "t_enforce_the_file_authentication", "eve_class4"
                )
                + "? (y/n)"
            )
            if value == "y" or value == "Y":
                self.eveAuthOpt = True
            elif value == "n" or value == "N":
                self.eveAuthOpt = False
        else:
            value = input(
                "(6/6) "
                + self.get_translation("t_no_authentication", "eve_class4")
                + "? (y/n)"
            )
            if value == "y" or value == "Y":
                self.eveAuthOpt = False
            elif value == "n" or value == "N":
                self.eveAuthOpt = True
        print(
            "\t(6) "
            + self.get_translation("t_the_file_auth", "eve_class4")
            + ":"
            + str(self.eveAuthOpt)
        )
        del value

    def evePrintTheData(self, value):
        if value:
            i = 0
            data = str(value, "utf-8")
            data = data.split("\n")
            x = len(data)
            if x > 0:
                while i < x:
                    if data[i]:
                        print("\t" + str(data[i]))
                    i += 1
                del data

    def eveOperationStart(self):
        # need specify host and port
        # options: "auto" or "manual" or "file"
        self.options()
        value = input(
            self.get_translation(
                "t_do_you_want_to_change_the_scan_options", "eve_class4"
            )
            + "? (y/n)"
        )
        if value == "y" or value == "Y":
            self.eveSetOptions()
        else:
            print(self.get_translation("t_no_options_were_changed", "eve_class4") + ".")
        del value

        # HTTP Request
        # r = self.http.request('GET', 'http://' + str(self.eveHost) + ':' + str(self.evePort) + '/login/index.php?query=query_string&option=option_string#heading1')
        # HTTP Request (1-st possible method for Basic Authentication)
        # r = self.http.request('GET', 'http://username:password@' + str(self.eveHost) + ':' + str(self.evePort) + '/login/index.php')
        # HTTP Request (2-nd possible method for Basic Authentication)
        # r = self.http.request('GET', 'http://' + str(self.eveHost) + ':' + str(self.evePort) + '/login/index.php', headers={'Authorization': 'Basic Base64 Encoding of the username:password string'})
        # URI syntax
        # [scheme] : // [userinfo] @ [host] : [port] [path] ? [query] # [fragment]
        # URI Legenda example(s):
        # [scheme] = http(s)://
        # [userinfo] = username:password@
        # [host] = ip address or domain name space
        # [port] = :port number
        # [path] = /login/index.php
        # [query] = ?query=query_string&option=option_string (with delimiter "&" which stands like as operator AND)
        # [fragment] = #heading1

        # HTTP Request headers example
        # Host: 192.168.56.107:8080
        # User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv: 79.0) Gecko/20100101 Firefox/79.0
        # Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
        # Accept-Language: en-US, en;q=0.5
        # Accept-Encoding: gzip, deflate
        # Connection: keep-alive

        # Ethical robot, spider, crawler:
        # 1. at first it would check "robots.txt" file of the web portal and would proceed to scan what is allowed;
        # 2. next, during scan of web portal it would be able to check HTTP headers, META tags and specific HTML attributes for robots like:
        # 2.1 <meta name="robots" content="index" />
        # 2.2 X-Robots-Tag: index
        # Robots-Tag: index
        # 2.3 <... class="robots-nocontent"> ... </...>
        # Without Ethics in mind robot would skip information in "robots.txt" file and HTTP headers and META tags.

        # The Robots Exclusion Protocol or The Robots Exclusion Standard
        # Robots.TXT file, which should prevent any crawling in the site would look like this:
        # User-agent: *         # for all robots
        # Disallow: /           # disallow indexing of all pages
        # Crawl-Delay: number   # preferred delay between requests #TODO. For full-functioning crawler as must to have delay functionality
        # Robots.TXT not fit for hiding sensitive information.

        # First: http://www.robotstxt.org/norobots-rfc.txt
        # More: https://tools.ietf.org/id/bots
        # Example: www.webcrawler.com

        # Scan type: auto, manual, file
        # auto - automatic request(s) to guess username:password
        # manual - manually enter username:password unencoded value
        # file - text file with list of "username:password/n"
        print(
            self.get_translation("t_current_time", "eve_class4")
            + ": "
            + str(time.ctime())
        )
        if self.eveTypeOption == self.eveType[1]:
            # manual
            if self.eveAuthOpt == False:
                # no auth
                try:
                    r = self.http.request(
                        "GET",
                        str(self.eveProtocolOption)
                        + "://"
                        + str(self.eveHost)
                        + ":"
                        + str(self.evePort)
                        + str(self.evePath),
                        headers={
                            "Accept": "text/html, application/xhtml+xml, application/xml;q=0.9, */*;q=0.8",
                            "Accept-Encoding": "gzip, deflate",
                            "Accept-Language": "en-US, en;q=0.7, lt;q=0.6, *;q=0.5",
                            "Cache-Control": "max-age=0",  # We could cache the data depending by Expires header of the origin server, which we are requesting
                            "User-Agent": str(self.eveUA),
                        },
                    )
                    print(self.get_translation("t_result", "eve_class4") + ":")
                    print(
                        "0000000:"
                        + str(self.eveProtocolOption)
                        + "://"
                        + str(self.eveHost)
                        + ":"
                        + str(self.evePort)
                        + str(self.evePath)
                    )
                    print(
                        self.get_translation("t_status", "eve_class4")
                        + ": "
                        + str(r.status)
                    )
                    print(self.get_translation("t_content", "eve_class4") + ":")
                    self.evePrintTheData(r.data)
                    print(self.get_translation("t_headers_or_data", "eve_class4") + ":")
                    print(str(HTTPResponse.getheaders(r)))

                except:
                    print(
                        self.get_translation(
                            "e_check_connectivity_and_selected_options", "eve_class4"
                        )
                        + "."
                    )
                else:
                    del r

            else:
                # auth
                qu = ["", ""]
                try:
                    r = self.http.request(
                        "GET",
                        str(self.eveProtocolOption)
                        + "://"
                        + str(self.eveHost)
                        + ":"
                        + str(self.evePort)
                        + "/"
                        + str(self.eveAuth.hexdigest()),
                        headers={
                            "Accept": "text/html, application/xhtml+xml, application/xml;q=0.9, */*;q=0.8",
                            "Accept-Encoding": "gzip, deflate",
                            "Accept-Language": "en-US, en;q=0.7, lt;q=0.6, *;q=0.5",
                            "Cache-Control": "max-age=0",
                            "User-Agent": str(self.eveUA),
                        },
                    )
                    print(self.get_translation("t_result", "eve_class4") + ":")
                    print(
                        "0000000:"
                        + str(self.eveProtocolOption)
                        + "://"
                        + str(self.eveHost)
                        + ":"
                        + str(self.evePort)
                        + "/"
                        + str(self.eveAuth.hexdigest())
                    )
                    print(
                        self.get_translation("t_status", "eve_class4")
                        + ": "
                        + str(r.status)
                    )
                    print(self.get_translation("t_content", "eve_class4") + ":")
                    self.evePrintTheData(r.data)
                    qu = [str(r.status), str(r.data)]
                except:
                    print(
                        self.get_translation(
                            "e_check_connectivity_and_selected_options", "eve_class4"
                        )
                        + "."
                    )
                else:
                    del r

                ok = 0
                if qu:
                    if qu[1].find("Allow") >= 0 and qu[0].find("200") >= 0:
                        ok = 1
                        print(
                            "\t"
                            + self.get_translation(
                                "t_the_eve_are_authorized_for_actions", "eve_class4"
                            )
                            + "."
                        )
                    else:
                        print(
                            "\t"
                            + self.get_translation(
                                "t_the_eve_are_not_authorized_for_actions", "eve_class4"
                            )
                            + "."
                        )
                else:
                    print(
                        "\t"
                        + self.get_translation(
                            "t_the_eve_are_not_authorized_for_actions", "eve_class4"
                        )
                        + "."
                    )

                if ok:
                    try:
                        r = self.http.request(
                            "GET",
                            str(self.eveProtocolOption)
                            + "://"
                            + str(self.eveHost)
                            + ":"
                            + str(self.evePort)
                            + str(self.evePath),
                            headers={
                                "Accept": "text/html, application/xhtml+xml, application/xml;q=0.9, */*;q=0.8",
                                "Accept-Encoding": "gzip, deflate",
                                "Accept-Language": "en-US, en;q=0.7, lt;q=0.6, *;q=0.5",
                                "Cache-Control": "max-age=0",  # We could cache the data depending by Expires header of the origin server, which we are requesting
                                "User-Agent": str(self.eveUA),
                            },
                        )
                        print(self.get_translation("t_result", "eve_class4") + ":")
                        print(
                            "0000000:"
                            + str(self.eveProtocolOption)
                            + "://"
                            + str(self.eveHost)
                            + ":"
                            + str(self.evePort)
                            + str(self.evePath)
                        )
                        print(
                            self.get_translation("t_status", "eve_class4")
                            + ": "
                            + str(r.status)
                        )
                        print(self.get_translation("t_content", "eve_class4") + ":")
                        self.evePrintTheData(r.data)
                        print(
                            self.get_translation("t_headers_or_data", "eve_class4")
                            + ":"
                        )
                        print(str(HTTPResponse.getheaders(r)))

                    except:
                        print(
                            self.get_translation(
                                "e_check_connectivity_and_selected_options",
                                "eve_class4",
                            )
                            + "."
                        )
                    else:
                        del r

                    print(
                        self.get_translation(
                            "t_the_eve_capabilities_host_address", "eve_class4"
                        )
                        + ":"
                    )
                    rp = urllib.robotparser.RobotFileParser()
                    rp.set_url(
                        str(self.eveProtocolOption)
                        + "://"
                        + str(self.eveHost)
                        + ":"
                        + str(self.evePort)
                        + str(self.evePath)
                    )
                    rp.read()
                    req_rate = rp.request_rate("*")
                    print(
                        self.get_translation("t_request_rate", "eve_class4")
                        + ":"
                        + str(req_rate)
                    )
                    crl_dely = rp.crawl_delay("*")
                    print(
                        self.get_translation("t_crawl_delay", "eve_class4")
                        + ":"
                        + str(crl_dely)
                    )
                    allowed = rp.can_fetch(
                        "*", str(self.eveProtocolOption) + "://" + str(self.eveHost)
                    )
                    print(
                        self.get_translation(
                            "t_allowed_to_fetch_host_address", "eve_class4"
                        )
                        + ":"
                        + str(allowed)
                    )

        elif self.eveTypeOption == self.eveType[0]:
            # file
            try:
                filehandler = open(self.filename, "r")
                data = filehandler.read()
                self.fileContent = data
                del data
            except:
                print(
                    self.get_translation("e_cannot_read_from_file", "eve_class4")
                    + str(self.filename)
                )
            else:
                filehandler.close()

            if self.fileContent:
                i = 0
                data = self.fileContent.split("\n")
                x = len(data)

                while i < x:
                    if data[i]:
                        print(self.get_translation("t_result", "eve_class4") + ":")
                        ix = data[i].index(":")
                        xi = data[i].index("://")
                        ln = len(data[i])
                        ul = data[i][ix + 1 : ln]
                        url = ul + self.evePath
                        ulh = data[i][xi + 3 : ln]
                        uth = hashlib.md5(bytes(ulh + self.eveSecret, "utf-8"))

                        if self.eveAuthOpt == False:
                            # no auth
                            try:
                                r = self.http.request(
                                    "GET",
                                    str(url),
                                    headers={
                                        "Accept": "text/html, application/xhtml+xml, application/xml;q=0.9, */*;q=0.8",
                                        "Accept-Encoding": "gzip, deflate",
                                        "Accept-Language": "en-US, en;q=0.7, lt;q=0.6, *;q=0.5",
                                        "Cache-Control": "max-age=0",
                                        "User-Agent": str(self.eveUA),
                                    },
                                )
                                print(
                                    self.get_translation("t_status", "eve_class4")
                                    + ": "
                                    + str(r.status)
                                )
                                print(
                                    self.get_translation("t_content", "eve_class4")
                                    + ":"
                                )
                                self.evePrintTheData(r.data)
                                print(
                                    self.get_translation(
                                        "t_headers_or_data", "eve_class4"
                                    )
                                    + ":"
                                )
                                print(str(HTTPResponse.getheaders(r)))
                            except:
                                print(
                                    self.get_translation(
                                        "e_check_connectivity_and_selected_options",
                                        "eve_class4",
                                    )
                                    + "."
                                )
                            else:
                                del r

                        else:
                            # auth
                            qu = ["", ""]
                            try:
                                r = self.http.request(
                                    "GET",
                                    str(ul) + "/" + str(uth.hexdigest()),
                                    headers={
                                        "Accept": "text/html, application/xhtml+xml, application/xml;q=0.9, */*;q=0.8",
                                        "Accept-Encoding": "gzip, deflate",
                                        "Accept-Language": "en-US, en;q=0.7, lt;q=0.6, *;q=0.5",
                                        "Cache-Control": "max-age=0",
                                        "User-Agent": str(self.eveUA),
                                    },
                                )
                                print(str(data[i]) + "/" + str(uth.hexdigest()))
                                print(
                                    self.get_translation("t_status", "eve_class4")
                                    + ": "
                                    + str(r.status)
                                )
                                print(
                                    self.get_translation("t_content", "eve_class4")
                                    + ":"
                                )
                                self.evePrintTheData(r.data)
                                qu = [str(r.status), str(r.data)]
                            except:
                                print(
                                    self.get_translation(
                                        "e_check_connectivity_and_selected_options",
                                        "eve_class4",
                                    )
                                    + "."
                                )
                            else:
                                del r

                            ok = 0
                            if qu:
                                if qu[1].find("Allow") >= 0 and qu[0].find("200") >= 0:
                                    ok = 1
                                    print(
                                        "\t"
                                        + self.get_translation(
                                            "t_the_eve_are_authorized_for_actions",
                                            "eve_class4",
                                        )
                                        + "."
                                    )
                                else:
                                    print(
                                        "\t"
                                        + self.get_translation(
                                            "t_the_eve_are_not_authorized_for_actions",
                                            "eve_class4",
                                        )
                                        + "."
                                    )
                            else:
                                print(
                                    "\t"
                                    + self.get_translation(
                                        "t_the_eve_are_not_authorized_for_actions",
                                        "eve_class4",
                                    )
                                    + "."
                                )

                            if ok:
                                try:
                                    r = self.http.request(
                                        "GET",
                                        str(url),
                                        headers={
                                            "Accept": "text/html, application/xhtml+xml, application/xml;q=0.9, */*;q=0.8",
                                            "Accept-Encoding": "gzip, deflate",
                                            "Accept-Language": "en-US, en;q=0.7, lt;q=0.6, *;q=0.5",
                                            "Cache-Control": "max-age=0",
                                            "User-Agent": str(self.eveUA),
                                        },
                                    )
                                    print(str(data[i]) + str(self.evePath))
                                    print(
                                        self.get_translation("t_status", "eve_class4")
                                        + ": "
                                        + str(r.status)
                                    )
                                    print(
                                        self.get_translation("t_content", "eve_class4")
                                        + ":"
                                    )
                                    self.evePrintTheData(r.data)
                                    print(
                                        self.get_translation(
                                            "t_headers_or_data", "eve_class4"
                                        )
                                        + ":"
                                    )
                                    print(str(HTTPResponse.getheaders(r)))
                                except:
                                    print(
                                        self.get_translation(
                                            "e_check_connectivity_and_selected_options",
                                            "eve_class4",
                                        )
                                        + "."
                                    )
                                else:
                                    del r

                                print(
                                    self.get_translation(
                                        "t_the_eve_capabilities_host_address",
                                        "eve_class4",
                                    )
                                    + ":"
                                )
                                rp = urllib.robotparser.RobotFileParser()
                                rp.set_url(str(url))
                                rp.read()
                                req_rate = rp.request_rate("*")
                                print(
                                    self.get_translation("t_request_rate", "eve_class4")
                                    + ":"
                                    + str(req_rate)
                                )
                                crl_dely = rp.crawl_delay("*")
                                print(
                                    self.get_translation("t_crawl_delay", "eve_class4")
                                    + ":"
                                    + str(crl_dely)
                                )
                                allowed = rp.can_fetch("*", str(ul))
                                print(
                                    self.get_translation(
                                        "t_allowed_to_fetch_host_address", "eve_class4"
                                    )
                                    + ":"
                                    + str(allowed)
                                )

                        time.sleep(randrange(9, 10))
                    i += 1
                    if i == x:
                        break

                else:
                    print(
                        self.get_translation(
                            "t_no_operations_were_loaded", "eve_class4"
                        )
                        + "."
                    )
            print(self.get_translation("t_exiting", "eve_class4") + "...")
        else:
            pass
            # auto
