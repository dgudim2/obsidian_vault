import subprocess
import hashlib
import time
from random import choice
from random import randrange
from eve_util import EveUtil

class EveClass8:

    eveDir = ""
    eveFiles = []
    eveMime = False

    def __init__(self, utility):
        self.util = utility
        self.eveOperationName = self.get_translation('t_frigevest_procedure', 'eve_class8') + "."
        self.eveOperationDescription = self.get_translation('t_frigevest_procedure', 'eve_class8') + "."
        
        var_output = ''
        try:
            var_output = subprocess.check_output(['pwd'])
            var_output = var_output.strip()
        except (subprocess.CalledProcessError, FileNotFoundError) as error:
            print(self.get_translation('e_in_pwd_command', 'eve_class8') + ".")
        
        self.eveDir = str(var_output.decode('utf-8'))

    def notifications(self):
        pass
    
    def get_translation(self, name, module):
        # get Translation
        return self.util.eveUtilRush(name, module)
           
    def options(self):
        print(self.get_translation('t_loaded_operation', 'eve_class8') + ": " + self.get_translation('t_frigevest_procedure', 'eve_class8'))
        print(self.get_translation('t_digest_options', 'eve_class8') + ":")
        print("\t" + self.get_translation('t_directory', 'eve_class8') + ":" + str(self.eveDir))
        print("\t" + self.get_translation('t_mime', 'eve_class8') + ":" + str(self.eveMime))
    
    def eveSetOptions(self):
        print("\t" + self.get_translation('t_current_directory', 'eve_class8') + ":" + str(self.eveDir))
        
        value = input("(1/2) " + self.get_translation('t_change_directory', 'eve_class8') + "? (y/n)")
        if (value == "y" or value == "Y"):
            directory = input(self.get_translation('t_enter_the_directory', 'eve_class8') + ":\n")
            self.eveDir = str(directory)
        
        flLen = len(self.eveDir)
        if (flLen == 0):
            directory = input(self.get_translation('t_enter_the_directory', 'eve_class8') + ":\n")
            self.eveDir = str(directory)
        
        print("\t" + self.get_translation('t_current_directory', 'eve_class8') + ":" + str(self.eveDir))
        del(value)
        
        print("\t" + self.get_translation('t_show_mime_information', 'eve_class8') + ":" + str(self.eveMime))
        if (self.eveMime == False):
            value = input("(2/2) " + self.get_translation('t_enable_mime_information', 'eve_class8') + "? (y/n)")
            if (value == "y" or value == "Y"):
                self.eveMime = True
        else:
            value = input("(2/2) " + self.get_translation('t_disable_mime_information', 'eve_class8') + "? (y/n)")
            if (value == "y" or value == "Y"):
                self.eveMime = False
        print("\t" + self.get_translation('t_current_mime_information', 'eve_class8') + ":" + str(self.eveMime))
        del (value)
    
    def eveOperationStart(self):
        self.options()
        value = input(self.get_translation('t_do_you_want_to_change_the_digest_options', 'eve_class8') + "? (y/n)")
        if (value == "y" or value == "Y"):
            self.eveSetOptions()
        else:
            print(self.get_translation('t_no_options_were_changed', 'eve_class8') + ".")
        del (value)

        print(self.get_translation('t_current_time', 'eve_class8') + ": " + str(time.ctime()))
        print(self.get_translation('t_files', 'eve_class8') + ":")
        var_output = ""
        try:
            var_output = subprocess.check_output(['ls', '-1', str(self.eveDir)])
        except subprocess.CalledProcessError as error:
            print(self.get_translation('e_in_ls_command', 'eve_class8') + ".")
        
        var_output = str(var_output.decode('utf-8'))
        
        directory = ''
        if self.eveDir[-1] != '/':
            directory = str(self.eveDir) + '/'
        else:
            directory = str(self.eveDir)
        
        data = ''
        check = ''
        for line in var_output.splitlines():
            check = line.strip("'")
            
            var_output2 = ""
            try:
                var_output2 = subprocess.check_output(['file', directory + str(check)])
            except subprocess.CalledProcessError as error:
                print(self.get_translation('e_in_file_command', 'eve_class8') + ".")
            
            snd = ''
            var_output2 = str(var_output2.decode('utf-8'))
            result = var_output2.find(': directory')
            if result != -1:
                snd = "\n\t" + self.get_translation('t_note_this_is_a_directory', 'eve_class8') + "."
            else:
                with open(str(directory) + str(check), "rb") as fl:
                    fldg = hashlib.md5()
                    while block := fl.read(8192):
                        fldg.update(block)
                
                snd = "\n\t" + self.get_translation('t_md_five_sum', 'eve_class8') + ": " + fldg.hexdigest()
            
            mod = ''
            var_output25 = ""
            try:
                var_output25 = subprocess.check_output(['du', '-s', '--time', directory + str(check)])
            except subprocess.CalledProcessError as error:
                print(self.get_translation('e_in_du_command', 'eve_class8') + ".")
                    
            mod = "\n\t" + self.get_translation('t_size_mod', 'eve_class8') + ": " + var_output25.decode('utf-8')
            
            im = ''
            if (self.eveMime == True):
                var_output3 = ""
                try:
                    var_output3 = subprocess.check_output(['file', '--mime-type', directory + str(check)])
                except subprocess.CalledProcessError as error:
                    print(self.get_translation('e_in_file_mime_command', 'eve_class8') + ".")
                    
                im = "\n\t" + self.get_translation('t_mim_t', 'eve_class8') + ": " + var_output3.decode('utf-8')
            
            data = data + str(check) + ' ' + str(snd) + ' ' + str(mod) + ' ' + str(im) + "\n"
            del check
        
        print(str(data))
        
        print(self.get_translation('t_exiting', 'eve_class8') + "...")
