library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity lab2_2 is
	Port (
		i_bit : in  STD_LOGIC_VECTOR(0 to 7);
		o_bit : out STD_LOGIC_VECTOR(0 to 15)
	);
end lab2_2;

architecture behave of lab2_2 is
	begin
		
		o_bit(8 to 15) <= i_bit;

	end behave;
	
-- 00000001
-- 00000010
-- 00000011

-- 00000100
-- 00000101
-- 00000110

-- 00000111
-- 00001000
-- 00001001

-- 00001010