LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.std_logic_arith.all;
ENTITY lab5 IS
PORT(
	clock: 			IN  std_logic;
	data: 			IN  std_logic_vector  (31 DOWNTO 0);
	write_address: IN  std_logic_vector  (4  DOWNTO 0);
	read_address:  IN  std_logic_vector  (4  DOWNTO 0);
	write_enable:  IN  std_logic;
	output_1: 		OUT std_logic_vector  (31 DOWNTO 0)
);
END lab5;
ARCHITECTURE rtl OF lab5 IS
	TYPE mem IS ARRAY(0 TO 31) OF std_logic_vector(31 DOWNTO 0);
	SIGNAL ram_block : mem;
	SIGNAL write_flag : std_logic := '0';
	
	BEGIN
		PROCESS (clock, read_address)
		BEGIN
			IF (rising_edge(clock)) THEN
				IF (write_flag = '0') THEN
					ram_block(2) <= "00000000000000000000000000100100";
					ram_block(4) <= "00000000000000000000011111100111";
					ram_block(6) <= "00000000000000000000000000000100";
					ram_block(8) <= "00000000000000000000000000010101";
					ram_block(10) <="00000000000000000000100111000100";
					write_flag <= '1';
				END IF;
				IF (write_enable = '1') THEN
					ram_block(conv_integer(unsigned(write_address))) <= data;
				END IF;
				output_1 <= ram_block(conv_integer(unsigned(read_address)));
			END IF;
		END PROCESS;
	END rtl;