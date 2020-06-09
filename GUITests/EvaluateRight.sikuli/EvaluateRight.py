#Search by address and evaluate prices of a gas station as right
type("1590681811380.png", "Via Magenta 52/d")
wait("gs_list2.png")
click("item2.png")
click("1590682218881.png")
wait("1590682510947.png")
#Timestamp format was updated after acceptance testing
#String "null" for Car Sharing disappeared after acceptance testing
type(Key.PAGE_DOWN)
wait("1590682389345.png")
region = (Region(215,736,1467,66))
click(region.inside().find("1590682435952.png"))