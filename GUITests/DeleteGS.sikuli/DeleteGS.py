#Admin is already logged in
#Create a gas station from admin panel and delete the same gas station
type("1590680614590.png", "Prova")
type("1590680632902.png", "Piazza Sabotino")
wait("gs_list.png")
click("item.png")
click("1590680997694.png")
click("1590681011169.png")
click("1590681028476.png")
type(Key.PAGE_DOWN)
wait("1590681533349.png")
region = (Region(257,700,1412,111))
click(region.inside().find("1590680389825.png"))