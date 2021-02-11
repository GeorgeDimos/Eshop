LOCK
TABLES `category` WRITE;
INSERT INTO `category`
VALUES (1, 'Category 1'),
       (2, 'Category 2'),
       (3, 'Category 3');
UNLOCK
TABLES;

LOCK
TABLES `product` WRITE;
INSERT INTO `product`
VALUES (1, 'Product A',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Aliquam eleifend mi in nulla posuere. Lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet. Tincidunt id aliquet risus feugiat in ante metus dictum. Augue lacus viverra vitae congue eu. Sollicitudin aliquam ultrices sagittis orci. Neque sodales ut etiam sit amet nisl purus. Ullamcorper malesuada proin libero nunc consequat interdum varius sit. Tortor id aliquet lectus proin nibh nisl condimentum id. Adipiscing elit pellentesque habitant morbi tristique senectus. Mattis vulputate enim nulla aliquet porttitor lacus luctus accumsan.',
        3, 100.00, 1),
       (2, 'Product B',
        'Feugiat in fermentum posuere urna nec tincidunt praesent semper feugiat. Eu turpis egestas pretium aenean. Massa vitae tortor condimentum lacinia quis vel. Risus feugiat in ante metus dictum at tempor. Facilisi etiam dignissim diam quis enim lobortis scelerisque fermentum. Erat pellentesque adipiscing commodo elit at imperdiet dui accumsan. Enim tortor at auctor urna nunc id. Ornare suspendisse sed nisi lacus sed viverra tellus in. Viverra nam libero justo laoreet sit amet cursus sit. Ac ut consequat semper viverra nam libero justo. Posuere lorem ipsum dolor sit. Ut aliquam purus sit amet luctus. Integer vitae justo eget magna fermentum iaculis eu. Dictumst vestibulum rhoncus est pellentesque elit ullamcorper dignissim cras tincidunt. Tristique senectus et netus et malesuada fames ac turpis. Suspendisse potenti nullam ac tortor vitae purus. Sit amet tellus cras adipiscing enim eu turpis. Id diam maecenas ultricies mi eget mauris pharetra et. Morbi tincidunt ornare massa eget egestas purus viverra accumsan in.',
        2, 110.68, 1),
       (3, 'Product C',
        'In metus vulputate eu scelerisque felis imperdiet. Eget duis at tellus at urna condimentum mattis pellentesque. Ut sem viverra aliquet eget sit. Nunc sed id semper risus in hendrerit gravida rutrum quisque. Ut venenatis tellus in metus. Accumsan in nisl nisi scelerisque eu ultrices vitae. Vitae ultricies leo integer malesuada nunc vel risus. Malesuada proin libero nunc consequat. Nec sagittis aliquam malesuada bibendum arcu. Iaculis eu non diam phasellus vestibulum lorem sed. Ut etiam sit amet nisl purus.',
        0, 90.00, 1),
       (4, 'Product D',
        'Pharetra vel turpis nunc eget. Ullamcorper sit amet risus nullam eget. Interdum posuere lorem ipsum dolor sit amet consectetur. Ut placerat orci nulla pellentesque dignissim. Ac tortor vitae purus faucibus ornare suspendisse sed. Nec ullamcorper sit amet risus nullam eget. Massa sapien faucibus et molestie ac feugiat. Mauris commodo quis imperdiet massa tincidunt nunc pulvinar. Sit amet volutpat consequat mauris nunc congue nisi. Dictum at tempor commodo ullamcorper a lacus. Mi sit amet mauris commodo quis imperdiet. Facilisis mauris sit amet massa vitae tortor condimentum lacinia quis. Fermentum dui faucibus in ornare quam. Libero enim sed faucibus turpis in eu mi.',
        3, 120.86, 2),
       (5, 'Product E',
        'Porta nibh venenatis cras sed. Interdum velit laoreet id donec ultrices tincidunt arcu non sodales. Urna cursus eget nunc scelerisque viverra. Vulputate sapien nec sagittis aliquam malesuada bibendum arcu. Non sodales neque sodales ut etiam sit amet. Venenatis cras sed felis eget velit aliquet sagittis id. Non enim praesent elementum facilisis leo vel fringilla est ullamcorper. Dis parturient montes nascetur ridiculus. Pellentesque habitant morbi tristique senectus. Interdum varius sit amet mattis vulputate enim nulla aliquet porttitor. Viverra nibh cras pulvinar mattis nunc sed. Aliquet nec ullamcorper sit amet risus nullam eget. Risus sed vulputate odio ut enim blandit volutpat maecenas volutpat. Consequat id porta nibh venenatis cras. Orci porta non pulvinar neque. Aliquet sagittis id consectetur purus ut faucibus pulvinar elementum integer. Nulla facilisi cras fermentum odio.',
        2, 210.80, 2),
       (6, 'Product F',
        'Vel pharetra vel turpis nunc eget lorem dolor. Non curabitur gravida arcu ac tortor dignissim. Velit egestas dui id ornare arcu odio. Id volutpat lacus laoreet non curabitur. Odio ut sem nulla pharetra diam. Cras fermentum odio eu feugiat. Arcu odio ut sem nulla pharetra diam sit. Blandit cursus risus at ultrices mi. Id donec ultrices tincidunt arcu non sodales neque sodales ut. Duis ut diam quam nulla porttitor massa id. Habitant morbi tristique senectus et. Tellus pellentesque eu tincidunt tortor aliquam. Eget mauris pharetra et ultrices. Tellus cras adipiscing enim eu turpis egestas pretium aenean pharetra. Et tortor consequat id porta. Nibh ipsum consequat nisl vel pretium lectus quam id. Quam viverra orci sagittis eu volutpat.',
        4, 210.80, 3),
       (7, 'Product G',
        'Leo vel fringilla est ullamcorper eget nulla facilisi. Donec enim diam vulputate ut pharetra. At tempor commodo ullamcorper a lacus vestibulum. Tempus urna et pharetra pharetra. Purus in mollis nunc sed id semper risus. Tempor orci eu lobortis elementum nibh. Et ligula ullamcorper malesuada proin. Mi tempus imperdiet nulla malesuada pellentesque. Nec feugiat in fermentum posuere urna. Vitae congue eu consequat ac felis. Nulla porttitor massa id neque aliquam vestibulum. Cras adipiscing enim eu turpis egestas pretium aenean. Lorem mollis aliquam ut porttitor leo a diam sollicitudin tempor. Mattis vulputate enim nulla aliquet porttitor. Arcu ac tortor dignissim convallis. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Pharetra convallis posuere morbi leo urna molestie at elementum.',
        4, 210.80, 3),
       (8, 'Product H',
        'Dui id ornare arcu odio. Varius morbi enim nunc faucibus a. Pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at. Nulla pharetra diam sit amet nisl suscipit adipiscing bibendum est. Eget nunc lobortis mattis aliquam faucibus. Mattis molestie a iaculis at. Libero enim sed faucibus turpis in eu mi bibendum neque. Sapien pellentesque habitant morbi tristique senectus. Eget sit amet tellus cras adipiscing enim eu. Neque gravida in fermentum et sollicitudin. Interdum velit laoreet id donec ultrices tincidunt arcu non sodales. At tempor commodo ullamcorper a lacus vestibulum sed arcu. Lacus vestibulum sed arcu non odio euismod lacinia at quis. Lectus magna fringilla urna porttitor rhoncus dolor purus non enim. Lacinia at quis risus sed vulputate odio ut enim. Ac ut consequat semper viverra.',
        4, 110.80, 2),
       (9, 'Product I',
        'Ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue eget. Turpis egestas pretium aenean pharetra magna. Ac ut consequat semper viverra nam libero justo laoreet. Amet luctus venenatis lectus magna fringilla. Eu facilisis sed odio morbi quis commodo. Enim nunc faucibus a pellentesque. Risus commodo viverra maecenas accumsan lacus vel facilisis. Morbi tristique senectus et netus et malesuada fames ac. Est pellentesque elit ullamcorper dignissim. Non pulvinar neque laoreet suspendisse interdum consectetur libero id. Consequat ac felis donec et odio pellentesque. Hac habitasse platea dictumst quisque sagittis purus. Viverra maecenas accumsan lacus vel facilisis volutpat.',
        4, 114.00, 3),
       (10, 'Product J',
        'Sed adipiscing diam donec adipiscing tristique risus nec feugiat. Scelerisque mauris pellentesque pulvinar pellentesque habitant morbi. Cras sed felis eget velit aliquet sagittis id. Scelerisque viverra mauris in aliquam sem fringilla. Tempus iaculis urna id volutpat lacus laoreet. Lacus viverra vitae congue eu consequat ac felis donec. Auctor elit sed vulputate mi sit amet mauris commodo quis. Ullamcorper eget nulla facilisi etiam dignissim diam quis. Nulla aliquet enim tortor at auctor urna nunc id. Eu mi bibendum neque egestas. Neque ornare aenean euismod elementum nisi quis eleifend. Arcu felis bibendum ut tristique et egestas quis ipsum. Purus faucibus ornare suspendisse sed nisi lacus sed. Nunc sed velit dignissim sodales ut eu sem.',
        4, 210.00, 1),
       (11, 'Product K',
        'Nam aliquam sem et tortor consequat id porta nibh venenatis. Aenean et tortor at risus viverra adipiscing at in tellus. Volutpat est velit egestas dui id ornare. Rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Pretium viverra suspendisse potenti nullam ac tortor. Dolor purus non enim praesent elementum facilisis. Elit pellentesque habitant morbi tristique senectus et netus et malesuada. Pretium lectus quam id leo in vitae turpis massa sed. Duis tristique sollicitudin nibh sit amet commodo. Nunc mattis enim ut tellus. Lacinia at quis risus sed vulputate odio. Dignissim suspendisse in est ante. Morbi non arcu risus quis varius.',
        4, 122.40, 1),
       (12, 'Product L',
        'Enim diam vulputate ut pharetra sit amet aliquam. Sit amet facilisis magna etiam. Convallis aenean et tortor at risus viverra. Posuere morbi leo urna molestie at elementum eu facilisis sed. Vulputate sapien nec sagittis aliquam malesuada bibendum. Rhoncus urna neque viverra justo nec ultrices dui sapien. Nisl suscipit adipiscing bibendum est ultricies. Tristique sollicitudin nibh sit amet commodo nulla facilisi. Senectus et netus et malesuada fames ac. Duis ut diam quam nulla porttitor massa id neque. Elementum facilisis leo vel fringilla est ullamcorper eget nulla facilisi. Scelerisque felis imperdiet proin fermentum leo. Sodales neque sodales ut etiam sit amet nisl purus in. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor commodo ullamcorper a lacus. Donec enim diam vulputate ut pharetra. Amet porttitor eget dolor morbi non arcu. Tellus mauris a diam maecenas sed. Lectus proin nibh nisl condimentum id venenatis a condimentum vitae. Pulvinar pellentesque habitant morbi tristique senectus et netus et.',
        4, 150.30, 2),
       (13, 'Product M',
        'Sit amet dictum sit amet justo donec enim. Vel risus commodo viverra maecenas accumsan lacus. Pretium lectus quam id leo in. Quis lectus nulla at volutpat diam ut venenatis tellus. Nunc faucibus a pellentesque sit amet porttitor eget. Rhoncus mattis rhoncus urna neque viverra justo. Pellentesque massa placerat duis ultricies lacus sed turpis tincidunt id. Ultricies tristique nulla aliquet enim tortor. Ultrices eros in cursus turpis massa tincidunt. Nec nam aliquam sem et. Dignissim convallis aenean et tortor at risus. Proin libero nunc consequat interdum varius sit amet. Nunc sed blandit libero volutpat sed. Sit amet mattis vulputate enim nulla aliquet.',
        4, 240.20, 2),
       (14, 'Product N',
        'Maecenas pharetra convallis posuere morbi leo. Mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et. Sed euismod nisi porta lorem mollis aliquam ut. Lobortis feugiat vivamus at augue eget arcu dictum varius. Ultrices tincidunt arcu non sodales neque sodales ut etiam. Adipiscing at in tellus integer feugiat. Lobortis feugiat vivamus at augue eget arcu. Eu scelerisque felis imperdiet proin fermentum leo vel orci porta. Id donec ultrices tincidunt arcu. Scelerisque in dictum non consectetur a erat nam at lectus. Ultrices in iaculis nunc sed augue lacus. Cum sociis natoque penatibus et magnis dis parturient. Massa massa ultricies mi quis hendrerit. Lorem ipsum dolor sit amet consectetur adipiscing elit ut aliquam.',
        4, 50.50, 2),
       (15, 'Product O',
        'Diam maecenas ultricies mi eget mauris pharetra. Volutpat commodo sed egestas egestas. Viverra mauris in aliquam sem. Viverra adipiscing at in tellus integer feugiat scelerisque. Faucibus pulvinar elementum integer enim neque volutpat ac tincidunt. Est lorem ipsum dolor sit amet consectetur adipiscing. Aliquam id diam maecenas ultricies mi eget mauris. Rhoncus urna neque viverra justo. Urna molestie at elementum eu facilisis. Sapien et ligula ullamcorper malesuada. Porta nibh venenatis cras sed. Id eu nisl nunc mi ipsum faucibus. Eu augue ut lectus arcu bibendum at.',
        4, 30.00, 3),
       (16, 'Product P',
        'Id faucibus nisl tincidunt eget. Velit laoreet id donec ultrices tincidunt arcu non sodales. Natoque penatibus et magnis dis parturient montes. In arcu cursus euismod quis viverra. Quam nulla porttitor massa id neque aliquam vestibulum morbi blandit. Ullamcorper dignissim cras tincidunt lobortis. Gravida neque convallis a cras semper. Faucibus vitae aliquet nec ullamcorper sit amet risus nullam eget. Ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue. Et tortor at risus viverra adipiscing at in tellus integer. Viverra maecenas accumsan lacus vel facilisis volutpat. In ante metus dictum at tempor commodo ullamcorper.',
        1, 100.00, 1),
       (17, 'Product Q',
        'Ut porttitor leo a diam sollicitudin tempor id eu nisl. Ultricies mi quis hendrerit dolor magna eget est. Rhoncus mattis rhoncus urna neque viverra justo nec ultrices dui. Eget felis eget nunc lobortis mattis aliquam faucibus. Nec dui nunc mattis enim ut tellus. Porttitor rhoncus dolor purus non enim praesent elementum facilisis. Velit euismod in pellentesque massa placerat. In mollis nunc sed id semper risus in hendrerit gravida. In arcu cursus euismod quis viverra nibh. Purus sit amet luctus venenatis lectus magna fringilla. Donec adipiscing tristique risus nec feugiat.',
        3, 110.68, 1),
       (18, 'Product R',
        'Lacinia quis vel eros donec ac odio. Pellentesque nec nam aliquam sem. Lobortis mattis aliquam faucibus purus in. Donec massa sapien faucibus et molestie ac feugiat sed. Quis ipsum suspendisse ultrices gravida dictum. Laoreet sit amet cursus sit amet dictum sit. Sodales ut etiam sit amet nisl purus. Natoque penatibus et magnis dis parturient montes. Posuere morbi leo urna molestie at. Sit amet luctus venenatis lectus magna fringilla urna porttitor. Egestas tellus rutrum tellus pellentesque eu tincidunt tortor aliquam. Nisl pretium fusce id velit. Tincidunt ornare massa eget egestas purus viverra. Dictum varius duis at consectetur lorem donec. Id interdum velit laoreet id donec ultrices. Elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue. Vitae elementum curabitur vitae nunc sed.',
        3, 90.00, 1),
       (19, 'Product S',
        'Integer eget aliquet nibh praesent tristique magna sit amet. Velit aliquet sagittis id consectetur. Adipiscing at in tellus integer feugiat scelerisque varius. Aliquam ultrices sagittis orci a scelerisque. Viverra tellus in hac habitasse platea dictumst vestibulum. Dignissim suspendisse in est ante. Purus gravida quis blandit turpis. Tincidunt nunc pulvinar sapien et ligula. Amet est placerat in egestas erat imperdiet. Iaculis nunc sed augue lacus viverra vitae.',
        2, 120.86, 2),
       (20, 'Product T',
        'Erat nam at lectus urna duis. Commodo nulla facilisi nullam vehicula ipsum a arcu cursus. Ut sem nulla pharetra diam. Rhoncus est pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat. At quis risus sed vulputate odio ut enim blandit. Neque laoreet suspendisse interdum consectetur libero id faucibus nisl tincidunt. Amet tellus cras adipiscing enim eu turpis egestas. Nunc vel risus commodo viverra maecenas accumsan lacus vel facilisis. Viverra ipsum nunc aliquet bibendum enim facilisis. Amet mauris commodo quis imperdiet massa tincidunt. Pretium quam vulputate dignissim suspendisse in est ante in. Varius duis at consectetur lorem donec massa sapien faucibus.',
        5, 210.80, 2),
       (21, 'Product U',
        'Ultrices vitae auctor eu augue ut lectus arcu bibendum at. Etiam erat velit scelerisque in. Leo urna molestie at elementum eu facilisis sed odio. Tempus egestas sed sed risus pretium quam vulputate dignissim. Et pharetra pharetra massa massa ultricies mi. Parturient montes nascetur ridiculus mus mauris vitae ultricies. At quis risus sed vulputate odio. Pharetra pharetra massa massa ultricies mi quis hendrerit. Scelerisque fermentum dui faucibus in ornare quam viverra. Eu nisl nunc mi ipsum. Pharetra sit amet aliquam id diam maecenas ultricies. Diam phasellus vestibulum lorem sed. Mi ipsum faucibus vitae aliquet. Integer quis auctor elit sed vulputate mi.',
        10, 210.80, 2),
       (22, 'Product V',
        'Est ultricies integer quis auctor elit sed vulputate. Eu sem integer vitae justo. Elementum curabitur vitae nunc sed velit. Mauris ultrices eros in cursus turpis. Nibh sit amet commodo nulla facilisi nullam vehicula ipsum a. Penatibus et magnis dis parturient montes nascetur. Diam maecenas ultricies mi eget mauris pharetra et. Amet est placerat in egestas erat imperdiet. Pretium vulputate sapien nec sagittis aliquam malesuada bibendum arcu vitae. Odio aenean sed adipiscing diam. Sodales neque sodales ut etiam sit. Malesuada pellentesque elit eget gravida cum sociis. Cursus turpis massa tincidunt dui. Aliquam sem fringilla ut morbi. Viverra orci sagittis eu volutpat. Aenean pharetra magna ac placerat vestibulum lectus mauris. Platea dictumst quisque sagittis purus sit amet volutpat. Eget gravida cum sociis natoque penatibus et magnis dis parturient.',
        3, 210.80, 1),
       (23, 'Product W',
        'Turpis egestas integer eget aliquet nibh praesent. Pellentesque diam volutpat commodo sed egestas egestas. Tristique risus nec feugiat in fermentum posuere urna nec tincidunt. Ullamcorper a lacus vestibulum sed arcu non odio euismod. Vitae congue mauris rhoncus aenean. Non curabitur gravida arcu ac tortor dignissim. Sed lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi. Curabitur vitae nunc sed velit dignissim. Tincidunt arcu non sodales neque sodales ut. Vitae ultricies leo integer malesuada nunc vel risus commodo viverra. Non arcu risus quis varius quam quisque id diam vel. Ac turpis egestas maecenas pharetra convallis posuere morbi leo urna. Sed euismod nisi porta lorem mollis aliquam ut porttitor leo. Vitae auctor eu augue ut lectus arcu. Arcu risus quis varius quam quisque id diam vel quam. In hac habitasse platea dictumst. Faucibus ornare suspendisse sed nisi lacus. Vitae turpis massa sed elementum tempus egestas sed. Vitae tempus quam pellentesque nec. Sit amet nisl suscipit adipiscing bibendum est ultricies.',
        1, 110.80, 2),
       (24, 'Product X',
        'Viverra aliquet eget sit amet tellus cras adipiscing. Vitae purus faucibus ornare suspendisse sed nisi. Quis eleifend quam adipiscing vitae proin. Faucibus a pellentesque sit amet porttitor eget dolor. Sit amet est placerat in egestas erat imperdiet. Viverra mauris in aliquam sem fringilla ut. Arcu ac tortor dignissim convallis aenean. Diam sollicitudin tempor id eu. Metus aliquam eleifend mi in nulla posuere sollicitudin aliquam. Tellus molestie nunc non blandit massa enim nec dui. Sit amet nisl suscipit adipiscing bibendum est ultricies integer. Tempor orci eu lobortis elementum nibh tellus molestie nunc. Tempus iaculis urna id volutpat lacus laoreet non curabitur gravida.',
        0, 114.00, 3),
       (25, 'Product Y',
        'Elementum facilisis leo vel fringilla est ullamcorper eget nulla facilisi. Posuere urna nec tincidunt praesent semper feugiat nibh sed. Venenatis lectus magna fringilla urna porttitor rhoncus dolor. Aliquam vestibulum morbi blandit cursus risus at ultrices mi. Nunc sed velit dignissim sodales ut eu sem integer vitae. Sed nisi lacus sed viverra tellus in. Mauris pharetra et ultrices neque ornare aenean euismod elementum nisi. Ut tortor pretium viverra suspendisse potenti nullam. Purus gravida quis blandit turpis cursus in hac. Cras tincidunt lobortis feugiat vivamus at augue eget arcu. Nunc sed augue lacus viverra vitae congue eu consequat.',
        1, 210.00, 1),
       (26, 'Product Z',
        'Erat imperdiet sed euismod nisi porta lorem. Quis auctor elit sed vulputate. In pellentesque massa placerat duis ultricies lacus sed turpis tincidunt. Nisl nunc mi ipsum faucibus vitae aliquet. Id consectetur purus ut faucibus pulvinar elementum integer enim neque. Nisl pretium fusce id velit ut. Bibendum ut tristique et egestas quis ipsum suspendisse. Quis hendrerit dolor magna eget est. Fringilla ut morbi tincidunt augue. Facilisis magna etiam tempor orci eu lobortis elementum nibh. Phasellus faucibus scelerisque eleifend donec pretium vulputate sapien nec sagittis. Diam vel quam elementum pulvinar. Orci sagittis eu volutpat odio facilisis mauris sit amet massa.',
        2, 122.40, 2),
       (27, 'Product AA',
        'At lectus urna duis convallis. Sagittis orci a scelerisque purus semper. Tellus molestie nunc non blandit. Bibendum ut tristique et egestas quis ipsum. Consequat semper viverra nam libero justo laoreet sit. Mauris rhoncus aenean vel elit. Nec tincidunt praesent semper feugiat nibh sed pulvinar proin. Venenatis tellus in metus vulputate eu scelerisque felis. Tortor consequat id porta nibh venenatis cras. Mollis aliquam ut porttitor leo a diam sollicitudin tempor. Sit amet purus gravida quis blandit turpis cursus in. Tellus in hac habitasse platea dictumst vestibulum. Est placerat in egestas erat imperdiet sed.',
        2, 150.30, 1),
       (28, 'Product AB',
        'Sit amet volutpat consequat mauris nunc congue nisi. Ipsum suspendisse ultrices gravida dictum. Et tortor consequat id porta nibh venenatis cras sed felis. Cras sed felis eget velit. Mi quis hendrerit dolor magna eget. Etiam sit amet nisl purus in mollis nunc sed id. Felis eget nunc lobortis mattis aliquam faucibus. Vulputate mi sit amet mauris commodo. Felis imperdiet proin fermentum leo. Pretium fusce id velit ut tortor pretium viverra suspendisse. Sed libero enim sed faucibus turpis in. Mattis pellentesque id nibh tortor id aliquet lectus. Posuere ac ut consequat semper. Eget aliquet nibh praesent tristique magna. Elementum facilisis leo vel fringilla est ullamcorper eget nulla facilisi. Amet risus nullam eget felis eget nunc lobortis.',
        7, 240.20, 2),
       (29, 'Product AC',
        'Odio facilisis mauris sit amet massa vitae tortor condimentum. Ut consequat semper viverra nam libero. Nibh nisl condimentum id venenatis a. Tristique nulla aliquet enim tortor at auctor urna. Duis convallis convallis tellus id interdum velit laoreet id. Sed felis eget velit aliquet. Dui accumsan sit amet nulla facilisi morbi tempus. Sapien pellentesque habitant morbi tristique senectus et netus et. Nibh tellus molestie nunc non blandit. Platea dictumst quisque sagittis purus sit. Euismod nisi porta lorem mollis aliquam ut. Eget arcu dictum varius duis at consectetur. Aliquam sem et tortor consequat id porta nibh venenatis. Euismod in pellentesque massa placerat. Vestibulum morbi blandit cursus risus at. Porttitor rhoncus dolor purus non. Orci eu lobortis elementum nibh tellus.',
        1, 50.50, 3),
       (30, 'Product AD',
        'Viverra maecenas accumsan lacus vel facilisis volutpat est velit. Eget mauris pharetra et ultrices neque ornare aenean. Mattis molestie a iaculis at erat pellentesque adipiscing commodo. Tempor nec feugiat nisl pretium fusce. Posuere morbi leo urna molestie at elementum eu facilisis sed. Donec et odio pellentesque diam volutpat commodo sed. Faucibus vitae aliquet nec ullamcorper. Mi quis hendrerit dolor magna eget est lorem ipsum. Arcu felis bibendum ut tristique et. Elementum tempus egestas sed sed risus pretium quam vulputate dignissim. Ultricies mi quis hendrerit dolor magna. Consectetur lorem donec massa sapien faucibus et molestie ac feugiat.',
        12, 30.00, 2);
UNLOCK
TABLES;

LOCK
TABLES `image` WRITE;
INSERT INTO `image`
VALUES (1, 'image_name_1', 17),
       (2, 'image_name_2', 24),
       (3, 'image_name_3', 6),
       (4, 'image_name_4', 2),
       (5, 'image_name_5', 30),
       (6, 'image_name_6', 7),
       (7, 'image_name_7', 7),
       (8, 'image_name_8', 13),
       (9, 'image_name_9', 15),
       (10, 'image_name_10', 10),
       (11, 'image_name_11', 13),
       (12, 'image_name_12', 28),
       (13, 'image_name_13', 26),
       (14, 'image_name_14', 30),
       (15, 'image_name_15', 12),
       (16, 'image_name_16', 8),
       (17, 'image_name_17', 19),
       (18, 'image_name_18', 22),
       (19, 'image_name_19', 5),
       (20, 'image_name_20', 25),
       (21, 'image_name_21', 28),
       (22, 'image_name_22', 22),
       (23, 'image_name_23', 6),
       (24, 'image_name_24', 26),
       (25, 'image_name_25', 1),
       (26, 'image_name_26', 26),
       (27, 'image_name_27', 14),
       (28, 'image_name_28', 17),
       (29, 'image_name_29', 19),
       (30, 'image_name_30', 5),
       (31, 'image_name_31', 21),
       (32, 'image_name_32', 9),
       (33, 'image_name_33', 10),
       (34, 'image_name_34', 14),
       (35, 'image_name_35', 2),
       (36, 'image_name_36', 15),
       (37, 'image_name_37', 29),
       (38, 'image_name_38', 14),
       (39, 'image_name_39', 12),
       (40, 'image_name_40', 6),
       (41, 'image_name_41', 13),
       (42, 'image_name_42', 13),
       (43, 'image_name_43', 16),
       (44, 'image_name_44', 20),
       (45, 'image_name_45', 17),
       (46, 'image_name_46', 30),
       (47, 'image_name_47', 4),
       (48, 'image_name_48', 22),
       (49, 'image_name_49', 25),
       (50, 'image_name_50', 3);
UNLOCK
TABLES;

LOCK
TABLES `users` WRITE;
INSERT INTO `users`
VALUES (1, 'admin', '$2a$04$QtDywltFkExfswHqKZmZtePwAIDUhIFiAOmaHvE0YYdE48pZnSlUy', 1);
UNLOCK
TABLES;

LOCK
TABLES `user_details` WRITE;
INSERT INTO `user_details`
VALUES (1, 'admin', 'admin', 'admin@a', 1);
UNLOCK
TABLES;


LOCK
TABLES `authorities` WRITE;
INSERT INTO `authorities`
VALUES (1, 'admin', 1);
UNLOCK
TABLES;