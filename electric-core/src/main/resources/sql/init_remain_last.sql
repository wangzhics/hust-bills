insert into e_record_charge_last(buildingName, roomName, stamp, power, money)
select e_room.buildingName, e_room.roomName, t2.stamp, t2.power, t2.money from
e_room left join 
(select t1.buildingName, t1.roomName, t1.stamp, e_record_charge.power, e_record_charge.money from 
(select buildingName, roomName, max(stamp) stamp from e_record_charge group by buildingName, roomName) as t1
left join e_record_charge 
on e_record_charge.buildingName = t1.buildingName and e_record_charge.roomName = t1.roomName and e_record_charge.stamp = t1.stamp) as t2
on e_room.buildingName = t2.buildingName and e_room.roomName = t2.roomName;