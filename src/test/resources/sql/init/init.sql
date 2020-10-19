-- Item category
insert into item_category(id, category_name)
values ('itemCat1', 'vegetable');
insert into item_category(id, category_name)
values ('itemCat2', 'meat');
insert into item_category(id, category_name)
values ('itemCat3', 'fruits');
insert into item_category(id, category_name)
values ('itemCat4', 'drugs');
insert into item_category(id, category_name)
values ('itemCat5', 'candies');

-- Items
insert into item(id,name,description,price,count,deleted, item_category_id)
values('item1','chocolate','chocolate',35.0,10,false,'itemCat5');
insert into item(id,name,description,price,count,deleted, item_category_id)
values('item2','pork meat','pork meat',100.0,6,false,'itemCat2');
insert into item(id,name,description,price,count,deleted, item_category_id)
values('item3','apple','apple',10.0,5,false,'itemCat3');
insert into item(id,name,description,price,count,deleted, item_category_id)
values('item4','carrot','carrot',5.0,7,false,'itemCat1');

-- Orders
insert into "order"(id, price_sum, status,created_at)
values ('order1',40.0,'NEW',CURRENT_TIMESTAMP);

insert into "order"(id,price_sum,status,created_at)
values ('order2',75.0,'COMPLETED',CURRENT_TIMESTAMP);

insert into "order"(id,price_sum,status,created_at)
values ('order3',35.0,'COMPLETED','2020-10-19T12:00:00');

-- Order items
insert into order_item(id,count,item_price,item_price_sum, item_id, order_id)
values ('orderItem1',3,10.0,30.0,'item3','order1');
insert into order_item(id,count,item_price,item_price_sum, item_id, order_id)
values ('orderItem2',2,5.0,10.0,'item4','order1');

insert into order_item(id,count,item_price,item_price_sum, item_id, order_id)
values ('orderItem3',2,10.0,20.0,'item3','order2');
insert into order_item(id,count,item_price,item_price_sum, item_id, order_id)
values ('orderItem4',4,5.0,20.0,'item4','order2');
insert into order_item(id,count,item_price,item_price_sum, item_id, order_id)
values ('orderItem5',1,35.0,35.0,'item1','order2');

insert into order_item(id,count,item_price,item_price_sum, item_id, order_id)
values ('orderItem6',1,35.0,35.0,'item1','order3');