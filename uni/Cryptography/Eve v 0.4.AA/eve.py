from eve_class1 import EveClass1
from eve_class3 import EveClass3
from eve_class4 import EveClass4
from eve_class5 import EveClass5
from eve_class8 import EveClass8
from eve_util import EveUtil


class Eve:

    # operations = (EveClass1, EveClass2, EveClass3)
    operations = (EveClass1, EveClass3, EveClass4, EveClass5, EveClass8)
    operation = [EveClass1]

    def __init__(self):
        self.util = EveUtil()
        self.eve = self.operation[0](self.util)

    def get_translation(self, name):
        # get Translation
        return self.util.eveUtilRush(name)
    
    def showSettings(self):
        # display Settings
        print(self.get_translation('t_settings_menu') + ":")
        print(" 1)  " + self.get_translation('t_show_this_settings_menu'))
        print(" 11) " + self.get_translation('t_show_current_options'))
        print(" 2)  " + self.get_translation('t_show_available_operations'))
        print(" 22) " + self.get_translation('t_select_operation') + " (" + self.get_translation('t_current') + ": " + str(self.currentOperation()) + ")" )
        print(" 3)  " + self.get_translation('t_operation_start'))
        print(" 4)  " + self.get_translation('t_language_settings'))
        print(" 0)  " + self.get_translation('t_exit') + "\n")

    def showCurrentOptions(self):
        # display current options
        print(self.get_translation('t_current_operations') + ":")
        self.eve.options()
        print("")

    def showOperationList(self, value):
        # display all possible operations
        op_list = []

        for item in self.operations:
            y = item(self.util)
            op_dict = {}
            op_dict["OperationName"] = y.eveOperationName
            op_dict["OperationDescription"] = y.eveOperationDescription
            op_list.append(op_dict)

        i = 0
        x = len(op_list)
        while i < x:
            i += 1
            print(str(i) + ". " + op_list[i-1]["OperationName"])
            if value == "full":
                print(op_list[i-1]["OperationDescription"])
            if i == x:
                break
        else:
            print(self.get_translation('t_no_operations_were_loaded') + ".")

        print("")

    def selectOperation(self, value):
        x = len(self.operations)
        select = int(value)
        select -= 1
        if(select >= 0 and select < x):
            self.operation = [self.operations[select]]
            del(self.eve)
            # TODO. Port Options to new Operation
            self.eve = self.operation[0](self.util)
            print(self.get_translation('t_operation_were_selected') + ": " + str(self.currentOperation()))
        else:
            print(self.get_translation('t_no_operations_were_selected') + ".")

    def currentOperation(self):
        if(self.operation):
            x = self.operation[0]
            y = x(self.util)
            return y.eveOperationName
        else:
            return ""

    def getUtil(self):
    	wtr = {
    	    "Polish": self.get_translation('t_polish'),
    	    "English": self.get_translation('t_english'),
    	    "Lithuanian": self.get_translation('t_lithuanian'),
    	}
    	
    	print(self.get_translation('t_current') + ": ")
    	for ou in self.util.eveUtil:
            print("\t" + ou + " (" + wtr.get(ou, self.get_translation('t_none')) + ")")


obj = Eve()

run = 22

obj.showSettings()

while(run == 22):

    value = input(obj.get_translation('t_select_operation_according_the_settings_menu') + ": \n")

    if(int(value) == 0):
        print("\n" + obj.get_translation('t_exiting') + "...")
        run = 0
        break
    elif(int(value) == 1):
        # Show this Settings Menu
        obj.showSettings()
    elif(int(value) == 11):
        # Show current Options
        obj.showCurrentOptions()
    elif(int(value) == 2):
        # Show available Operations
        obj.showOperationList("full")
    elif(int(value) == 22):
        # Select the Operation
        obj.showOperationList("min")
        sel = input(obj.get_translation('t_please_select_the_operation') + ":")
        obj.selectOperation(sel)
    elif(int(value) == 3):
        # Start the Operation
        if(obj.eve):
            obj.eve.eveOperationStart()
        else:
            print(obj.get_translation('t_no_actions_were_processed_select_the_operation') + ".")
    elif(int(value) == 4):
        # Everything about Translation
        obj.getUtil()
        wide = input(obj.get_translation('t_choose_language') + " ...  \n")
        if(wide == "Polish" or wide == "polish" or wide == "polski" or wide == "lenkų"):
            obj.util.eveSetPolish()
        elif(wide == "Lithuanian" or wide == "lithuanian" or wide == "litewski" or wide == "lietuvių"):
            obj.util.eveSetLithuanian()
        else:
            obj.util.eveSetEnglish()
        obj.util.eveCheckLanguage()
    else:
        print("\n" + obj.get_translation('t_restarting') + "...")

        obj.showSettings()

        run = 22
