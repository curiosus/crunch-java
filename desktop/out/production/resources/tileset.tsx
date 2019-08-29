<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.2" tiledversion="1.2.4" name="tileset" tilewidth="64" tileheight="64" tilecount="4" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <image width="64" height="64" source="walltile.png"/>
 </tile>
 <tile id="1">
  <image width="64" height="64" source="floor.png"/>
 </tile>
 <tile id="2">
  <properties>
   <property name="destination" type="bool" value="true"/>
  </properties>
  <image width="64" height="64" source="box.png"/>
 </tile>
 <tile id="3">
  <properties>
   <property name="spawnpoint" type="bool" value="true"/>
  </properties>
  <image width="64" height="64" source="spawntile.png"/>
 </tile>
</tileset>
