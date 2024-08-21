setb = [1,2,3,4,5,6,7,8,9,10,11,12]
setc = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19]

bxc = set();
cxb = set();

for sb in setb:
    for sc in setc:
        bxc.add(str(sb)+","+str(sc))


for sc in setc:
    for sb in setb:
        cxb.add(str(sc)+","+str(sb))


print(len(bxc.intersection(cxb)))
print(len(bxc.union(cxb)))
