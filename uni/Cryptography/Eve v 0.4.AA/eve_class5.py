import urllib3
import base64
import subprocess
import os
import time
from eve_util import EveUtil


class EveClass5:

    eveScan = {"The Number and Question": "The Answer"}
    eveType = ("LinuxOS", "MacOS (Not)", "WindowsOS (Not)", "AndroidOS (Not)")
    eveTypeOption = [0, 0, 0, 0]
    eveSupAndSubUser = 0
    eveInventory = {}

    def __init__(self, utility):
        self.util = utility
        self.eveOperationName = self.get_translation('t_configuration_check_of_the_operating_system', 'eve_class5') + "."
        self.eveOperationDescription = self.get_translation('eve_class_five_description', 'eve_class5') + "."
        # self.http = urllib3.PoolManager()
    
    def get_translation(self, name, module):
        # get Translation
        return self.util.eveUtilRush(name, module)

    def options(self):
        print(self.get_translation('t_loaded_operation', 'eve_class5') + ": " + self.get_translation('t_configuration_check_of_the_operating_system', 'eve_class5'))
        print(self.get_translation('t_available_scan_options', 'eve_class5') + ":")

        for item in self.eveType:
            print("\t" + item)

    def eveSetOptions(self):
        print("\t" + self.get_translation('t_select_os_for_the_scan', 'eve_class5') + ":")
        i = 0
        for item in self.eveType:
            value = input(self.get_translation('t_do_you_want_to_scan_the', 'eve_class5') + item + "? (y/n)")
            if value == "y" or value == "Y":
                self.eveTypeOption[i] = 1
            else:
                self.eveTypeOption[i] = 0
            del value
            i += 1

    def eveOperationStart(self):
        # need specify for which OS Configuration Scan gonna be performed
        self.options()
        value = input(self.get_translation('t_do_you_want_to_scan_only_first_option', 'eve_class5') + "? (y/n)")
        if value == "y" or value == "Y":
            self.eveTypeOption[-1] = 1
        else:
            self.eveSetOptions()
        del value

        # Scan type: by selected OS and defined Custom Configuration in this One File
        # LinuxOS - developed by Linus Torvalds and Companies which using it as open sourced product
        # MacOS - developed by Apple Company
        # WindowsOS - developed by Microsoft Company
        # AndroidOS - developed by Google and Companies which using it as open sourced product
        print(self.get_translation('t_current_time', 'eve_class5') + ": " + str(time.ctime()))
        time.sleep(1)

        i = 0
        for item in self.eveType:
            if item.find("LinuxOS") and self.eveTypeOption[i] == 1:
                print(self.get_translation('t_linuxos_scan_commence', 'eve_class5') + ":")
                self.eveLinuxOSScan()
            elif item.find("MacOS") and self.eveTypeOption[i] == 1:
                print(self.get_translation('t_macos_scan_commence', 'eve_class5') + ":")
                self.eveMacOSScan()
            elif item.find("WindowsOS") and self.eveTypeOption[i] == 1:
                print(self.get_translation('t_windows_scan_commence', 'eve_class5') + ":")
                self.eveWindowsOSScan()
            elif item.find("AndroidOS") and self.eveTypeOption[i] == 1:
                print(self.get_translation('t_android_scan_commence', 'eve_class5') + ":")
                self.eveAndroidOSScan()
            else:
                pass
            i += 1

    def eveLinuxOSScan(self):
        
        print('')
        print(self.get_translation('t_os_prod_information', 'eve_class5') + ":")
        print(os.system('uname -a'))
        print('')
        y = 1
        var_user = ''
        var_output = ''
        
        print("--- " + self.get_translation('t_initial_actual_information', 'eve_class5') + " ---")
        print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " Q. " + self.get_translation('t_does_the_current_user_has_administrative_access_to_the_system', 'eve_class5') + "?")
        
        try:
            var_user = subprocess.check_output(['whoami'])
            user = var_user.decode('ascii').split('\n')
            user = str(user[0])
        except subprocess.CalledProcessError as error:
            print(self.get_translation('e_no', 'eve_class5') + str(y))
        
        try:
            var_output = subprocess.check_output(['groups', str(user)])
            print(var_output)
        except subprocess.CalledProcessError as error:
            print(self.get_translation('e_no', 'eve_class5') + str(y))
        
        if b'sudo' in var_output:
            self.eveSupAndSubUser = 1
            self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_admin_access_is_in_place_for_checking_the_configuration', 'eve_class5') + "?"] = self.get_translation('t_everything_seems_right', 'eve_class5') + "."
            print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_everything_seems_right_there_exists_possibility_for_admin_access', 'eve_class5') + ".")
        elif b'adm' in var_output:
            self.eveSupAndSubUser = 1
            self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_admin_access_is_in_place_for_checking_the_configuration', 'eve_class5') + "?"] = self.get_translation('t_everything_seems_right', 'eve_class5') + "."
            print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_everything_seems_right_there_exists_possibility_for_admin_access', 'eve_class5') + ".")
        elif b'wheel' in var_output:
            self.eveSupAndSubUser = 1
            self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_admin_access_is_in_place_for_checking_the_configuration', 'eve_class5') + "?"] = self.get_translation('t_everything_seems_right', 'eve_class5') + "."
            print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_everything_seems_right_there_exists_possibility_for_admin_access', 'eve_class5') + ".")
        elif b'root' in var_output:
            self.eveSupAndSubUser = 1
            self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_admin_access_is_in_place_for_checking_the_configuration', 'eve_class5') + "?"] = self.get_translation('t_it_gonna_work', 'eve_class5') + "."
            print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_it_gonna_work_has_admin_access', 'eve_class5') + ".")
        else:
            self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_admin_access_is_in_place_for_checking_the_system_configuration', 'eve_class5') + "?"] = self.get_translation('t_no', 'eve_class5') + "."
            print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_no', 'eve_class5') + ".")
        
        del var_user, var_output

        print('')
        y += 1
        var_output = ''
        var_error = ''
            
        print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " Q. " + self.get_translation('t_does_exists_access_to_credentials_file_and_does_accounts_are_with_empty_password_field', 'eve_class5') + "?")
        
        if self.eveSupAndSubUser == 1:
            try:
                result = subprocess.run(
                ['sudo','awk','-F:','($2 == "") { print $1 " does not have password" }','/etc/shadow'], capture_output=True, text=True
                )
            except subprocess.CalledProcessError as error:
                print(self.get_translation('e_no', 'eve_class5') + str(y))
                print(error)
                print(result.stderr)
            
            var_output = result.stdout
            
            if self.checkValueInData("Permission denied", var_output) == '-1':
                self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_the_user_have_permission_to_access_the_shadow_file', 'eve_class5') + "?"] = self.get_translation('t_this_user_does_not_have_read_access_of_the_shadow_file', 'eve_class5') + "."
                print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_this_user_cannot_access_system_privileges', 'eve_class5') + ".")
            else:
                if not var_output:
                    self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_exists_accounts_with_empty_password_field', 'eve_class5') + "?"] = self.get_translation('t_all_accounts_has_passwords_and_not_empty', 'eve_class5') + "."
                    print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_all_accounts_has_passwords_and_not_empty', 'eve_class5') + ".")
                else:
                    self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_exists_accounts_with_empty_password_field', 'eve_class5') + "?"] = self.get_translation('t_need_to_check_for_passwordless_accounts', 'eve_class5') + "."
                    print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_need_to_check_for_passwordless_accounts', 'eve_class5') + ".")
            
            del var_output
        else:
            try:
                result = subprocess.run(
                ['awk','-F:','($2 == "") { print $1 "does not have password" }','/etc/shadow'], capture_output=True, text=True
                )
            except subprocess.CalledProcessError as error:
                print(self.get_translation('e_no', 'eve_class5') + str(y))
                print(error)
                print(result.stderr)
                
            var_output = result.stdout
            
            if self.checkValueInData("Permission denied", var_output) == '-1':
                self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_the_user_have_permission_to_access_the_shadow_file', 'eve_class5') + "?"] = self.get_translation('t_this_user_does_not_have_read_access_of_the_shadow_file', 'eve_class5') + "."
                print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + 't_this_user_cannot_access_system_privileges' + ".")
            else:
                if not var_output:
                    self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_exists_accounts_with_empty_password_field', 'eve_class5') + "?"] = self.get_translation('t_all_accounts_has_passwords_and_not_empty', 'eve_class5') + "."
                    print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_all_accounts_has_passwords_and_not_empty', 'eve_class5') + ".")
                else:
                    self.eveScan[self.get_translation('t_number', 'eve_class5') + ":" + str(y) + ". " + self.get_translation('t_does_exists_accounts_with_empty_password_field', 'eve_class5') + "?"] = self.get_translation('t_need_to_check_for_passwordless_accounts', 'eve_class5') + "."
                    print(self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " A. " + self.get_translation('t_need_to_check_for_passwordless_accounts', 'eve_class5') + ".")
            
            del var_output
        
        print('')
        y += 1
        var_operation = ''
        list_operations = ("H", "LI", "L")
        
        print("--- " + self.get_translation('t_check_available_applications', 'eve_class5') + " ---")
        
        """
        " 1st value of @var eveInventory is meant for choosing the Operation:
        " Op Codename:
        " H - Help
        " LI - Launch and return Output Only
        " L - Launch
        """
        self.eveInventory["apparmor module"] = ("L", "apparmor_status")
        self.eveInventory["OpenSSL"] = ("LI", "openssl version")
        self.eveInventory["nmap.org"] = ("H", "nmap", "nmap.ncat -h", "nmap.nping -h")
        
        for key, value in self.eveInventory.items():
            var_operation = value[0]
            i = -1
            for val in value:
                i = i + 1
                    
                if i != 0 and var_operation == "H":
                    print("\n" + self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " C. " + key + ":")
                    try:
                        result = subprocess.run([val,'-h'], shell=True, capture_output=True)
                    except subprocess.CalledProcessError as error:
                        print(self.get_translation('e_no', 'eve_class5') + str(y))
                    
                    if(result.stdout.decode('UTF-8') != ""):
                        print(" " + result.stdout.decode('UTF-8'))
                    elif(result.stderr.decode('UTF-8') != ""):
                        prop = result.stderr.decode('UTF-8').split(':', maxsplit=2)
                        print(prop[2])
                    else:
                        print(self.get_translation('t_no_idea_about', 'eve_class5') + " " + key)
                
                if i != 0 and var_operation == "L":
                    print("\n" + self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " C. " + key + ":")
                    try:
                         result = subprocess.run([val], shell=True, capture_output=True)
                    except subprocess.CalledProcessError as error:
                         print(self.get_translation('e_no', 'eve_class5') + str(y))
                    
                    if(result.stdout.decode('UTF-8') != ""):
                        print(" " + result.stdout.decode('UTF-8'))
                    elif(result.stderr.decode('UTF-8') != ""):
                        prop = result.stderr.decode('UTF-8').split(':', maxsplit=2)
                        print(prop[2])
                    else:
                        print(self.get_translation('t_no_idea_about', 'eve_class5') + " " + key)
                
                if i != 0 and var_operation == "LI":
                    print("\n" + self.get_translation('t_number', 'eve_class5') + ":" + str(y) + " C. " + key + ":")
                    try:
                        result = subprocess.run([val,'version'], shell=True, capture_output=True)
                    except subprocess.CalledProcessError as error:
                        print(self.get_translation('e_no', 'eve_class5') + ": " + str(y))
                    
                    if(result.stdout.decode('UTF-8') != ""):
                        print(" " + result.stdout.decode('UTF-8'))
                    elif(result.stderr.decode('UTF-8') != ""):
                        prop = result.stderr.decode('UTF-8').split(':', maxsplit=2)
                        print(prop[2])
                    else:
                        print(self.get_translation('t_no_idea_about', 'eve_class5') + " " + key)

        del var_operation, list_operations


    def eveMacOSScan(self):
        print(self.get_translation('t_currently_not_available', 'eve_class5') + ".")

    def eveWindowsOSScan(self):
        print(self.get_translation('t_currently_not_available', 'eve_class5') + ".")

    def eveAndroidOSScan(self):
        print(self.get_translation('t_currently_not_available', 'eve_class5') + ".")

    def checkValueInData(self, value, data):
        return data.find(value)
        
    
