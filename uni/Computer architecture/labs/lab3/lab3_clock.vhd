library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity lab3_clock is
	Port (
		clock : in  STD_LOGIC;
		led   : out STD_LOGIC_VECTOR(0 to 7)
	);
end lab3_clock;

architecture behave of lab3_clock is
	signal clk_counter : natural range 0 to 25000000 := 0;
	signal blinker : std_logic := '0';
	begin
		
		process(clock)
		begin 
			if rising_edge(clock) then 
				clk_counter <= clk_counter + 1; 
				if clk_counter >= 25000000 then 
				  blinker <= not blinker;
				  clk_counter <= 0;
				end if; 
			end if; 
		end process;
		
		led(0) <= blinker;
		led(1) <= blinker;
		led(2) <= blinker;
		led(3) <= blinker;
		led(4) <= '1';
		led(5) <= '1';
		led(6) <= blinker;
		led(7) <= '1';
		
	end behave;