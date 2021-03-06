Kodekata: Bowling-kalkulator
============================

En kodekata er en øvelse for å utvikle kodeferdigheter,
og design av løsninger.
Det er en anbefalt tilnærming å jobbe parvis og testdrevet;
en person skriver testen, den andre implementerer den.

# Rules:
- Each game, or “line” of bowling, includes ten turns, or “frames” for the bowler.
- In each frame, the bowler gets up to two tries to knock down all the pins.
- If in two tries, he fails to knock them all down, his score for that frame is the total number of pins knocked down in his two tries.
- If in two tries he knocks them all down, this is called a “spare” and his score for the frame is ten plus the number of pins knocked down on his next throw 
  (in his next turn).
- If on his first try in the frame he knocks down all the pins, this is called a “strike”. 
  His turn is over, and his score for the frame is ten plus the simple total of the pins knocked down in his next two rolls.
- If he gets a spare or strike in the last (tenth) frame, the bowler gets to throw one or two more bonus balls, respectively. 
  These bonus throws are taken as part of the same turn. If the bonus throws knock down all the pins, the process does not repeat: 
  the bonus throws are only used to calculate the score of the final frame.
- The game score is the total of all frame scores.

# Legend
When scoring “X” indicates a strike, “/” indicates a spare, “-” indicates a miss

#Test cases
1. Gutter game = all zeroes, (score = 0)
   00000000000000000000
2. One pin down in each roll, (score = 20)
   11111111111111111111
3. Almost spare in every frame, (score = 90)
   90182736459018273645
4. Spare in first roll, one pin down in each other roll, (score = 10 + 1 + 18 = 29)
   9/111111111111111111
5. Spare in last roll, one pin down in each other roll, (score = 18 + 10 + 1 = 29)
   1111111111111111111/1
6. Strike in first roll, one pin down in each other roll, (score = 10 + 1 + 1 + 18 = 30)
   X111111111111111111
7. Strike in last roll, one pin down in each other roll, (score = 18 + 10 + 1 + 1 = 30)
   111111111111111111X11
8. Golden game = all strikes (score = 300)
   XXXXXXXXXXXX
