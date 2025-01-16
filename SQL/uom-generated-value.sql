INSERT INTO uoms (
    uom_type,
    uom_sub_type,
    uom_quantity,
    uom_level,
    uom_weight,
    uom_status,
    uom_item_id
) VALUES
(
    'SINGLE', 
    'SINGLE', 
    12, 
    7, 
    5.5,
    'AVAILABLE',
    26083
),
(
    'CASE', 
    'UNIT', 
    12, 
    7, 
    5.5,
    'AVAILABLE',
    26083
),
(
    'PALLET', 
    'CASE', 
    10, 
    7, 
    100,
    'AVAILABLE',
    26083
);


SELECT i.*
uo.*
FROM items i
JOIN item_pictures ip ON i.item_id = ip.item_p_item_id;