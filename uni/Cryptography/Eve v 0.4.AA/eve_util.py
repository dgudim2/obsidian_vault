from defusedxml.ElementTree import parse
import sys

class Polish:

    no = 0
    active = False
    
    def __init__(self):
        self.eveUtilName = "Używać Polski język."
        if self.active == True:
            self.eveUtilDescription = "Polski język jest aktywny."
        elif self.active == False:
            self.eveUtilDescription = "Polish język nie jest aktywny."
        self.eveLang = "Polish"

    def setActive(self):
        self.active = True
        
    def deActivate(self):
        self.active = False
     

class English:

    no = 1
    active = False

    def __init__(self):
        self.eveUtilName = "Deploy English Language."
        if self.active == True:
            self.eveUtilDescription = "English Language is Active."
        elif self.active == False:
            self.eveUtilDescription = "English Language is Not Active."
        self.eveLang = "English"

    def setActive(self):
        self.active = True
        
    def deActivate(self):
        self.active = False


class Lithuanian:

    no = 2
    active = False

    def __init__(self):
        self.eveUtilName = "Naudoti lietuvių kalbą."
        if self.active == True:
            self.eveUtilDescription = "Lietuvių kalba yra aktyvi."
        elif self.active == False:
            self.eveUtilDescription = "Lietuvių kalba nėra aktyvi."
        self.eveLang = "Lithuanian"

    def setActive(self):
        self.active = True
        
    def deActivate(self):
        self.active = False


class EveUtil:

    eveUtil = ("Polish", "English", "Lithuanian")
    eveObj = (Polish(), English(), Lithuanian())
    eveDefault = 0
    eveLang = "Polish"

    def __init__(self):
        self.eveUtilName = "Deploy i18n."
        self.eveUtilDescription = "Change language according in-build settings."

    def options(self):
        print("Loaded Util: " + str(self.eveUtilName))
        print("Util Options:")
        print("\tLanguage:" + str(self.eveSelected))

    def eveSetOptions(self):
        pass

    def eveUtilRush(self, name_t, mod = ''):
        ete = parse('know-how.xml')
        emp = ""
        underroot = ete.getroot()
        for language in underroot.findall('language'):
            name_l = language.get('name')
            if name_l == self.eveLang:
                for classi in language.findall('class'):
                    name_c = classi.get('name') 
                    if name_c == 'language' or name_c == 'general' or (name_c == mod and mod != ''):
                        dt = classi.findtext(name_t)
                        if dt != None:
                            return dt
                            
        return emp                     

    def eveDeActivate(self):
    	self.eveLang = ""
    	self.eveDefault = 0
    	for v in self.eveObj:
             v.deActivate()
	
    def eveSetPolish(self):
    	self.eveDeActivate()
    	ix = self.eveUtil.index("Polish")
    	self.eveDefault = ix
    	self.eveObj[ix].setActive()
    	print('Polski język: ' + str(self.eveObj[ix].active))
    	self.eveObj[ix].__init__()
    	self.eveUtilName = getattr(self.eveObj[ix], 'eveUtilName')
    	self.eveUtilDescription = getattr(self.eveObj[ix], 'eveUtilDescription')
    	self.eveLang = getattr(self.eveObj[ix], 'eveLang')

    def eveSetEnglish(self):
    	self.eveDeActivate()
    	ix = self.eveUtil.index("English")
    	self.eveDefault = ix
    	self.eveObj[ix].setActive()
    	print('English language: ' + str(self.eveObj[ix].active))
    	self.eveObj[ix].__init__()
    	self.eveUtilName = getattr(self.eveObj[ix], 'eveUtilName')
    	self.eveUtilDescription = getattr(self.eveObj[ix], 'eveUtilDescription')
    	self.eveLang = getattr(self.eveObj[ix], 'eveLang')

    def eveSetLithuanian(self):
    	self.eveDeActivate()
    	ix = self.eveUtil.index("Lithuanian")
    	self.eveDefault = ix
    	self.eveObj[ix].setActive()
    	print('Lietuvių kalba: ' + str(self.eveObj[ix].active))
    	self.eveObj[ix].__init__()
    	self.eveUtilName = getattr(self.eveObj[ix], 'eveUtilName')
    	self.eveUtilDescription = getattr(self.eveObj[ix], 'eveUtilDescription')
    	self.eveLang = getattr(self.eveObj[ix], 'eveLang')
    	
    def eveCheckLanguage(self):
    	print(self.eveLang)


'''
get = EveUtil()
print(get.eveUtilDescription)
get.eveSetEnglish()
print(get.eveUtilDescription)
rs = get.eveUtilRush('t_settings_menu')
print(rs)
'''

