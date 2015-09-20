insert into e_record_remain_last(buildingName, roomName, stamp, remain)
select e_room.buildingName, e_room.roomName, t2.stamp, t2.remain from
e_room left join 
(select t1.buildingName, t1.roomName, t1.stamp, e_record_remain.remain from 
(select buildingName, roomName, max(stamp) stamp from e_record_remain group by buildingName, roomName) as t1
left join e_record_remain 
on e_record_remain.buildingName = t1.buildingName and e_record_remain.roomName = t1.roomName and e_record_remain.stamp = t1.stamp) as t2
on e_room.buildingName = t2.buildingName and e_room.roomName = t2.roomName;