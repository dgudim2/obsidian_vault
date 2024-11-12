import subprocess
import os
import time
from eve_util import EveUtil


class EveClass3:

    eveOS = "Windows"
    evePath = str('C:\\Program Files\\gs\\gs9.24\\bin')
    eveIOPath = str('C:\\new_project')
    eveInputFile = str('Sample_fe.pdf')
    eveOutputFile = str('Sample_fd.pdf')
    eveDir = str('\\')
    # Windows opt path - C:\Program Files\gs\gs9.24\bin\
    # Linux opt path - /usr/bin/gs

    def __init__(self, utility):
        self.util = utility
        self.eveOperationName = self.get_translation('t_guess_the_password_of_the_pdf_files', 'eve_class3') + "."
        self.eveOperationDescription = self.get_translation('eve_class_three_description', 'eve_class3') + "."
        un = ['','']
        gs = ['','']
        try:
            var_un = subprocess.check_output(['uname'])
            un = var_un.decode('ascii').split('\n')
            del(var_un)
        except (subprocess.CalledProcessError, FileNotFoundError) as error:
            print(self.get_translation('e_in_uname_command', 'eve_class3') + ".")
        
        try:
            chk = un.index('Linux')
        except (IndexError, ValueError) as error:
            print(self.get_translation('e_linux_is_not_in_the_list', 'eve_class3') + ".")
            chk = -1
        
        if(chk >= 0):
            self.eveOS = "Linux"
            self.eveIOPath = os.getcwd()
            self.eveDir = "/"
            try:
                var_gs = subprocess.check_output(['whereis', 'gs'])
                gs = var_gs.decode('ascii').split('\n')
                del(var_gs)
            except subprocess.CalledProcessError as error:
                print(self.get_translation('e_in_whereis_gs_command', 'eve_class3') + ".")
        else:
            self.eveOS = "Undefined"
        
        if(gs[0].find('/usr/bin/gs') >= 0):
            self.evePath = '/usr/bin/gs'

    def notifications(self):
        pass
    
    def get_translation(self, name, module):
        # get Translation
        return self.util.eveUtilRush(name, module)
    
    def options(self):
        print(self.get_translation('t_loaded_operation', 'eve_class3') + ": " + self.get_translation('t_guess_the_password_of_the_pdf_files', 'eve_class3'))
        print(" ")
        print(self.get_translation('t_dependency_the_interpreter_for_pdf_gs', 'eve_class3'))
        print(self.get_translation('t_path_to_the_gs', 'eve_class3') + ": " + str(self.evePath))
        print(self.get_translation('t_path_for_the_input_and_output', 'eve_class3') + ": " + str(self.eveIOPath))
        print(self.get_translation('t_input_file_name', 'eve_class3') + ": " + str(self.eveInputFile))
        print(self.get_translation('t_output_file_name', 'eve_class3') + ": " + str(self.eveOutputFile))
        print(" ")
        print(self.get_translation('t_current_full_path_to_the_input_file', 'eve_class3') + ":" + str(self.eveIOPath) + str(self.eveDir) + str(self.eveInputFile))
        print(self.get_translation('t_current_full_path_to_the_output_file', 'eve_class3') + ":" + str(self.eveIOPath) + str(self.eveDir) + str(self.eveOutputFile))

    def eveSetOptions(self):
        print("\t" + self.get_translation('t_current_path', 'eve_class3') + ":" + str(self.evePath))
        value = input("(1/3) " + self.get_translation('t_specify_new_path', 'eve_class3') + "? (y/n)")
        if (value == "y" or value == "Y"):
            spath = input(self.get_translation('t_enter_the_new_path', 'eve_class3') + ":\n")
            self.evePath = str(spath)
        print("\t(1) " + self.get_translation('t_current_path', 'eve_class3') + ":" + str(self.evePath))
        del (value)
        print("\t" + self.get_translation('t_current_input_file', 'eve_class3') + ":" + str(self.eveInputFile))
        value = input("(2/3) " + self.get_translation('t_specify_new_input_file', 'eve_class3') + "? (y/n)")
        if (value == "y" or value == "Y"):
            ifile = input(self.get_translation('t_enter_the_new_input_file', 'eve_class3') + ":\n")
            self.eveInputFile = str(ifile)
        print("\t(2) " + self.get_translation('t_current_input_file', 'eve_class3') + ":" + str(self.eveInputFile))
        del (value)
        print("\t" + self.get_translation('t_current_output_file', 'eve_class3') + ":" + str(self.eveOutputFile))
        value = input("(3/4) " + self.get_translation('t_specify_new_output_file', 'eve_class3') + "? (y/n)")
        if (value == "y" or value == "Y"):
            ifile = input(self.get_translation('t_enter_the_new_output_file', 'eve_class3') + ":\n")
            self.eveInputFile = str(ifile)
        print("\t(3) " + self.get_translation('t_current_output_file', 'eve_class3') + ":" + str(self.eveOutputFile))
        del (value)
        print("\t" + self.get_translation('t_current_path_for_the_input_and_output', 'eve_class3') + ":" + str(self.eveIOPath))
        value = input("(4/4) " + self.get_translation('t_specify_new_path_for_the_input_and_output', 'eve_class3') + "? (y/n)")
        if (value == "y" or value == "Y"):
            pifile = input(self.get_translation('t_enter_the_new_path_for_the_input_and_output', 'eve_class3') + ":\n")
            self.eveIOPath = str(pifile)
        print("\t(4) " + self.get_translation('t_current_path_for_the_input_and_output', 'eve_class3') + ":" + str(self.eveIOPath))
        print("\t(4) " + self.get_translation('t_current_full_path_to_the_input_file', 'eve_class3') + ":" + str(self.eveIOPath) + str(self.eveDir) + str(self.eveInputFile))
        print("\t(4) " + self.get_translation('t_current_full_path_to_the_output_file', 'eve_class3') + ":" + str(self.eveIOPath) + str(self.eveDir) + str(self.eveOutputFile))
        del (value)

    def subprocess_cmd(self, command):
        process = subprocess.Popen(command, stdout=subprocess.PIPE, shell=True)
        proc_stdout = process.communicate()[0].strip()
        print(proc_stdout)

    def eveOperationStart(self):
        self.options()
        value = input(self.get_translation('t_do_you_want_to_change_the_scan_options', 'eve_class3') + "? (y/n)")
        if (value == "y" or value == "Y"):
            self.eveSetOptions()
        else:
            print(self.get_translation('t_no_options_were_changed', 'eve_class3') + ".")
        del(value)

        y = 1

        value = input(self.get_translation('t_do_you_want_to_create_test_file', 'eve_class3') + "? (y/n)")
        if (value == "y" or value == "Y"):
            if (self.eveOS == "Linux"):
                command = 'gs -sDEVICE=pdfwrite -dCompatibilityLevel=1.4 ' \
                ' -dNOPROMPT -dNOPAUSE -dBATCH -dQUIET ' \
                ' -sOwnerPassword=mypassword -sUserPassword=3210 ' \
                ' -sOutputFile=' + str(self.eveIOPath) + str(self.eveDir) + str(self.eveOutputFile) + \
                ' -f ' + str(self.eveIOPath) + str(self.eveDir) + str(self.eveInputFile)
            else:
                command = 'cd "' + str(self.evePath) + '" & gswin64c ' \
                ' -sDEVICE=pdfwrite -dCompatibilityLevel=1.4 -dNOPROMPT -dNOPAUSE -dQUIET ' \
                ' -sOwnerPassword=mypassword -sUserPassword=3210 ' \
                ' -sOutputFile=' + str(self.eveIOPath) + str(self.eveDir) + str(self.eveOutputFile) + ' -dBATCH ' \
                + str(self.eveIOPath) + str(self.eveDir) + str(self.eveInputFile)

            try:
                os.system(command)
            except os.error as error:
                print(self.get_translation('e_no', 'eve_class3') + ": " + str(y))
                print(error)

        y = y + 1
        
        print(self.get_translation('t_current_time', 'eve_class3') + ": " + str(time.ctime()))
        print(self.get_translation('t_decryption_begin', 'eve_class3') + ".")
        
        if (self.eveOS == "Linux"):
            command_re = 'gs -q -dNOPAUSE -dBATCH -sDEVICE=pdfwrite -sPDFPassword=mypassword ' \
            ' -sOutputFile=' + str(self.eveIOPath) + str(self.eveDir) + str(self.eveInputFile) + '.bkp' \
            ' -f ' + str(self.eveIOPath) + str(self.eveDir) + str(self.eveOutputFile)
        else:
            command_re = 'cd "' + str(self.evePath) + '" & gswin64c ' \
            ' -sDEVICE=pdfwrite -dCompatibilityLevel=1.4 ' \
            ' -q -dNOPAUSE -dBATCH -sPDFPassword= ' \
            ' -sOutputFile=' + str(self.eveIOPath) + str(self.eveDir) + str(self.eveInputFile) + '.bkp' \
            ' -c -f ' + str(self.eveIOPath) + str(self.eveDir) + str(self.eveOutputFile)

        try:
            os.system(command_re)
        except os.error as error:
            print(self.get_translation('e_no', 'eve_class3') + ": " + str(y))
            print(error)
        
        print(self.get_translation('t_decryption_end', 'eve_class3') + ".")
        print(self.get_translation('t_current_time', 'eve_class3') + ": " + str(time.ctime()))

