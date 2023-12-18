package problem16;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SolutionPart2 {
    
    private static final char EMPTY = '.';
    private static final char VERTICAL_SPLITTER = '|';
    private static final char HORIZONTAL_SPLITTER = '-';
    private static final char LEANING_MIRROR = '\\';
    private static final char FALLING_MIRROR = '/';
    private static final Integer MAX_COL = 109;
    private static final Integer MAX_ROW = 109;

    public static void main(String... args) throws Exception {

        Integer maxTilesEnergised = 0;

        for(int row = 0; row <= MAX_ROW; row++) {
            Integer energisedTiles = getTilesEnergised(row, 0, Direction.RIGHT);

            if(maxTilesEnergised < energisedTiles) {
                maxTilesEnergised = energisedTiles;
            }

            energisedTiles = getTilesEnergised(row, MAX_COL, Direction.LEFT);

            if(maxTilesEnergised < energisedTiles) {
                maxTilesEnergised = energisedTiles;
            }
        }

        for(int col = 0; col <= MAX_COL; col++) {
            Integer energisedTiles = getTilesEnergised(0, col, Direction.DOWN);

            if(maxTilesEnergised < energisedTiles) {
                maxTilesEnergised = energisedTiles;
            }

            energisedTiles = getTilesEnergised(MAX_ROW, col, Direction.UP);

            if(maxTilesEnergised < energisedTiles) {
                maxTilesEnergised = energisedTiles;
            }
        }

        System.out.println("Max energised tiles: " + maxTilesEnergised);
    }

    public static Integer getTilesEnergised(int row, int col, Direction inputDirection) throws Exception {
        Set<PathStartingTile> analysedPathStartingTiles = new HashSet<PathStartingTile>();
        Set<PathStartingTile> pathStartingTiles = new HashSet<PathStartingTile>();
        List<List<Tile>> paths = new ArrayList<List<Tile>>();
        char[][] input = getInput();

        //Start in the top left tile heading right.
        pathStartingTiles.add(new PathStartingTile(new Tile(col,row), inputDirection));

        //Trace a path until we find a path splitter or a direction change. Add the new path starting 
        //nodes to the set, and then loop over all the starting nodes until we've traced all the paths
        while(!pathStartingTiles.isEmpty()) {
            PathStartingTile pathStartingTile = pathStartingTiles.iterator().next();

            //anti looping logic!
            if(analysedPathStartingTiles.contains(pathStartingTile)) {
                pathStartingTiles.remove(pathStartingTile);
                continue;
            }

            Tile currentTile = pathStartingTile.getStartingTile();
            Direction direction = pathStartingTile.getDirection();
            
            List<Tile> path = new ArrayList<Tile>();

            while (currentTile != null) {
                path.add(currentTile);
                char tileCharacter = input[currentTile.getRow()][currentTile.getCol()];

                if(tileCharacter == EMPTY) {
                    currentTile = currentTile.next(direction);
                } 
                else if (tileCharacter == LEANING_MIRROR) {

                    if(direction == Direction.RIGHT) {
                        Tile nextTile = currentTile.next(Direction.DOWN);
                        if(nextTile != null) {
                            pathStartingTiles.add(new PathStartingTile(nextTile, Direction.DOWN));
                        }
                    }
                    else if(direction == Direction.LEFT) {
                        Tile nextTile = currentTile.next(Direction.UP);
                        if(nextTile != null) {
                            pathStartingTiles.add(new PathStartingTile(nextTile, Direction.UP));
                        }
                    }
                    else if(direction == Direction.DOWN) {
                        Tile nextTile = currentTile.next(Direction.RIGHT);
                        if(nextTile != null) {
                            pathStartingTiles.add(new PathStartingTile(nextTile, Direction.RIGHT));
                        }
                    }
                    else if(direction == Direction.UP) {
                        Tile nextTile = currentTile.next(Direction.LEFT);
                        if(nextTile != null) {
                            pathStartingTiles.add(new PathStartingTile(nextTile, Direction.LEFT));
                        }
                    }

                    //Path has finished now
                    currentTile = null;
                } 
                else if (tileCharacter == FALLING_MIRROR) {

                    if(direction == Direction.RIGHT) {
                        Tile nextTile = currentTile.next(Direction.UP);
                        if(nextTile != null) {
                            pathStartingTiles.add(new PathStartingTile(nextTile, Direction.UP));
                        }
                    }
                    else if(direction == Direction.LEFT) {
                        Tile nextTile = currentTile.next(Direction.DOWN);
                        if(nextTile != null) {
                            pathStartingTiles.add(new PathStartingTile(nextTile, Direction.DOWN));
                        }
                    }
                    else if(direction == Direction.DOWN) {
                        Tile nextTile = currentTile.next(Direction.LEFT);
                        if(nextTile != null) {
                            pathStartingTiles.add(new PathStartingTile(nextTile, Direction.LEFT));
                        }
                    }
                    else if(direction == Direction.UP) {
                        Tile nextTile = currentTile.next(Direction.RIGHT);
                        if(nextTile != null) {
                            pathStartingTiles.add(new PathStartingTile(nextTile, Direction.RIGHT));
                        }
                    }

                    //Path has finished now
                    currentTile = null;
                } 
                else if (tileCharacter == VERTICAL_SPLITTER) {
                    if(direction == Direction.RIGHT || direction == Direction.LEFT) {
                        Tile upTile = currentTile.next(Direction.UP);
                        if(upTile != null) {
                            pathStartingTiles.add(new PathStartingTile(upTile, Direction.UP));
                        }

                        Tile downTile = currentTile.next(Direction.DOWN);
                        if(downTile != null) {
                            pathStartingTiles.add(new PathStartingTile(downTile, Direction.DOWN));
                        }

                        //Path has finished now
                        currentTile = null;
                    }
                    else if(direction == Direction.DOWN || direction == Direction.UP) {
                        currentTile = currentTile.next(direction);
                    }                    
                } 
                else if (tileCharacter == HORIZONTAL_SPLITTER) {
                    if(direction == Direction.RIGHT || direction == Direction.LEFT) {
                        currentTile = currentTile.next(direction);
                    }
                    else if(direction == Direction.DOWN || direction == Direction.UP) {
                        Tile rightTile = currentTile.next(Direction.RIGHT);
                        if(rightTile != null) {
                            pathStartingTiles.add(new PathStartingTile(rightTile, Direction.RIGHT));
                        }

                        Tile leftTile = currentTile.next(Direction.LEFT);
                        if(leftTile != null) {
                            pathStartingTiles.add(new PathStartingTile(leftTile, Direction.LEFT));
                        }

                        //Path has finished now
                        currentTile = null;
                    }
                }
            }

            paths.add(path);
            pathStartingTiles.remove(pathStartingTile);
            analysedPathStartingTiles.add(pathStartingTile);
        }

        //Now we can add all the Tiles in all the paths to a set and let the equals method determine if
        // they're unique. The number of energised tiles is then the set size.
        Set<Tile> energisedTiles = new HashSet<Tile>();
        for(List<Tile> path : paths) {
            for(Tile tile : path) {
                energisedTiles.add(tile);
            }
        }
        return energisedTiles.size();
    }

    public static class PathStartingTile {
        private Tile startingTile;
        private Direction direction;

        public PathStartingTile(Tile startingTile, Direction direction) {
            this.startingTile = startingTile;
            this.direction = direction;
        }

        public Direction getDirection() {
            return direction;
        }

        public Tile getStartingTile() {
            return startingTile;
        }

        @Override
        public int hashCode() {
            return startingTile.hashCode() * direction.ordinal();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof PathStartingTile) {
                PathStartingTile that = (PathStartingTile) obj;
                return this.startingTile.equals(that.getStartingTile()) && this.direction.equals(that.getDirection());
            }
            return false;
        }
    }

    public static enum Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT;
    }

    public static class Tile {
        private int col;
        private int row;

        public Tile(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public int getCol() {
            return this.col;
        }

        public int getRow() {
            return this.row;
        }

        public Tile next(Direction direction) throws Exception {
            switch(direction) {
                case UP :
                    if(this.row-1 < 0) {
                        return null;
                    }
                    return new Tile(this.col, (this.row-1)); 
                case DOWN :
                    if(this.row+1 > MAX_ROW) {
                        return null;
                    }
                    return new Tile(this.col, (this.row+1)); 
                case RIGHT :
                    if(this.col+1 > MAX_COL) {
                        return null;
                    }
                    return new Tile((this.col+1), this.row); 
                case LEFT :
                    if(this.col-1 < 0) {
                        return null;
                    }
                    return new Tile((this.col-1), this.row); 
                default :
                    throw new Exception("Unrecognised Direction"){};
            }
        }

        @Override
        public int hashCode() {
            return this.col * this.row;
        }

        @Override
        public boolean equals(Object object) {
            if(object instanceof Tile) {
                final Tile that = (Tile) object;
                return this.getCol() == that.getCol() && this.getRow() == that.getRow();
            }
            return false;
        }
    }

    private static char[][] getInput() {
        char[][] input = new char[MAX_ROW+1][MAX_COL+1];

        input[0] = "\\.......\\..//-.............-/....|....../-....|..................................|........--/.................".toCharArray();
		input[1] = "..|/..-..\\..........\\.....|......\\...-....../...................................................|.../||\\......".toCharArray();
		input[2] = "....././\\..........-|....|....-..........................\\.......|.............................-/\\............".toCharArray();
		input[3] = "...........\\..-...\\...................|.......\\/./../...|.....-.|....|.........................|..............".toCharArray();
		input[4] = "............|-..............-....................-...\\.........|.............-..................\\...|.....//..".toCharArray();
		input[5] = ".....\\......................./............|..................|...|.............................../...........|".toCharArray();
		input[6] = ".........-..-...|...\\....-........................../....-.........-|.......|......|....................-.....".toCharArray();
		input[7] = "............../..../...../..\\..../................../............../..\\.................././......./..........".toCharArray();
		input[8] = "...-..|.....-./\\...../..../.|..\\.......-........................../.\\.............\\...........................".toCharArray();
		input[9] = "............|.-..../.............................|......\\.........|......--....-.........-..................|.".toCharArray();
		input[10] = "..|..\\........../...\\\\............\\........\\-...........-....|.....\\....|..................|../...../.........".toCharArray();
		input[11] = "....|..-....................-./-...............................|......./........../....|.....\\.|..\\...\\.......".toCharArray();
		input[12] = "..../......................|.....|...............-..../\\......................-.......\\.......................".toCharArray();
		input[13] = "............................\\.|.-.|.............-.................................................-...........".toCharArray();
		input[14] = "...|.-.......................\\|..-......-.................|.......-..\\.......\\-..................|............".toCharArray();
		input[15] = ".............................\\......................\\.../.\\.|...........\\....|..\\.............................".toCharArray();
		input[16] = "-.../...|................/.-......./..........|.........|........./||.-.........\\.....\\...-............./.|...".toCharArray();
		input[17] = "........../......../-.........................................../.......-|.|....................|...|../...../".toCharArray();
		input[18] = "......|................|............./.......\\.............-..............\\|.......-..\\./...../.-.../....../..".toCharArray();
		input[19] = "/............|\\........../.......\\......-..|......../-../.........|\\....../............\\.........|...|........".toCharArray();
		input[20] = "...............-.........|........................../.........\\............-\\....\\......../..................|".toCharArray();
		input[21] = "................\\.\\................\\....-.......-.....-......|..|...\\/........./...../..|.............|.......".toCharArray();
		input[22] = "............................................................................................................/.".toCharArray();
		input[23] = "................................|..-/...................../.....-.............|...-...........................".toCharArray();
		input[24] = "...................-/..................||./.............|..........|..................-.../..................|".toCharArray();
		input[25] = "..................\\/...|...\\.-.........../....-......................-\\.......|......./.........|..|.\\..-.....".toCharArray();
		input[26] = "........................|.-..................-........................./................\\.../...\\...|.........".toCharArray();
		input[27] = "..................-.........-/.....\\......|...../\\...-......\\....-....................................../.....".toCharArray();
		input[28] = ".....................|..............\\........\\....\\...............-........../......\\...........\\.............".toCharArray();
		input[29] = "...\\./......................-.\\..........\\.-..\\........-.................-.......-.||......................./.".toCharArray();
		input[30] = "...|..............\\..|............\\....|............|.....\\......../...........-...................\\...../../.".toCharArray();
		input[31] = ".........\\.........................\\.............../........//.................|.........\\.-...............-..".toCharArray();
		input[32] = ".............\\....-..../..-................./...|/...-...................|../.../.............-......../.....|".toCharArray();
		input[33] = "-.....||/.......\\......../..............................|...............................................-.....".toCharArray();
		input[34] = ".......|......\\........../|........................../-........../.\\..\\..............-.......|/|....-.........".toCharArray();
		input[35] = "../-.............-.\\-........./........-........\\...|...........-.\\./..\\.\\....................................".toCharArray();
		input[36] = "/.\\................../.....|...........-............-.......-......./.\\.\\..|...........\\......../.............".toCharArray();
		input[37] = "..............................|.....-...|...\\.......-.........\\..\\-..........-....\\......|...........|/...-...".toCharArray();
		input[38] = ".......\\......\\\\......-.....-.....-........|../........\\..|.............\\../................../...\\......./..\\".toCharArray();
		input[39] = "..-.........|............./............./.-.|.....|...../..............................................\\...|..".toCharArray();
		input[40] = "|......................./......-....-............/.................//...........\\.............-..\\............".toCharArray();
		input[41] = "............\\.....................-......-..|.\\.......\\/..........\\............/....\\.../.....................".toCharArray();
		input[42] = "|.............................|........./........................\\.................../.........|........../\\..".toCharArray();
		input[43] = ".../...|/.-.......-.............\\......................................./........--..................\\........".toCharArray();
		input[44] = ".\\...........\\..-|............-...-.\\...../...........-..|.............././.................|........../......".toCharArray();
		input[45] = "...............-......................./..............\\..\\........................\\.............-../..........".toCharArray();
		input[46] = "................................\\..../..................\\.....................\\............/............./....".toCharArray();
		input[47] = "...-..../../.......-............-......|...../.........|............................|...../../..............-|".toCharArray();
		input[48] = ".........|.......-..../..........................................\\|.|./.....-..\\.-.....................|......".toCharArray();
		input[49] = "............\\............................................|................-...-................\\..............".toCharArray();
		input[50] = "|...................................-...|/......|............./........./...|...................|....\\........".toCharArray();
		input[51] = "|......\\-......................./.-.............../.|\\-..|................-.....|../-\\.|.-..-|.........-.|-\\.-".toCharArray();
		input[52] = "......-......|-......|.................-.-..\\..../-./.............................................|-......./..".toCharArray();
		input[53] = ".-...............................\\...........................-|.......-.......................................".toCharArray();
		input[54] = ".............\\.|....|....\\.............../..\\..|..................../.............|...-\\......................".toCharArray();
		input[55] = "........-.\\/.....................|........-......./............../..............\\..\\.............|......./....".toCharArray();
		input[56] = "..-....../-..........................|...../..\\...............\\..........-....\\...........|.\\...\\.............".toCharArray();
		input[57] = "...........|............................................../...................\\.\\.\\...........................".toCharArray();
		input[58] = "../.................|\\.|.\\..-..\\........\\...-..-........-..\\....-...............-..............\\...........|/.".toCharArray();
		input[59] = "........\\.|....................../...........//................/.................\\..../.....-.................".toCharArray();
		input[60] = ".....\\...\\.....\\.......\\......\\.....-|./..|............./|./|-\\.........................-........--...........".toCharArray();
		input[61] = "..|................../.....\\.....|.......-...|............-../................-...\\........-.......|..\\\\......".toCharArray();
		input[62] = ".....|......../....\\....../...\\........................................\\..\\..............-..................|.".toCharArray();
		input[63] = "....//......\\.........-......./.\\..\\........./..........-....-.....\\..-...|...................\\.../......|....".toCharArray();
		input[64] = "...............|...................../.\\.............-...\\.....\\..|..................../.............\\.....|..".toCharArray();
		input[65] = "\\.....................-............/.............\\...-...................../...-..-.....|./..............\\....".toCharArray();
		input[66] = "........................-../......\\.............-........-.\\/.....|.-..........-............../..-....-\\\\..-..".toCharArray();
		input[67] = ".........../.................\\.....|..../.\\.........|........./...-..........................\\................".toCharArray();
		input[68] = "|....................../..../.......................-................\\...................................-./..".toCharArray();
		input[69] = "...|.......\\-.......\\........\\..........-........................./...../.........................-../........".toCharArray();
		input[70] = ".............\\.......-....-..|...\\..../.-...\\......|....................|..-./...................\\............".toCharArray();
		input[71] = "...............\\./.\\...............|.............\\./......|...........................-.........../.|.........".toCharArray();
		input[72] = "...../....../..............|.................../.....|.....\\............................/.\\...../...\\|........".toCharArray();
		input[73] = ".\\............/......................-......./................-/./.|....................................-.....".toCharArray();
		input[74] = ".................../.......\\........../-.............................|\\.......................................".toCharArray();
		input[75] = "../....................\\....../......./.....................................|................-/\\...|..........".toCharArray();
		input[76] = "\\................/........................--.........../\\....\\../....../..........|-............-.............".toCharArray();
		input[77] = "./\\..................--......\\..../.....\\.......-..............................|.............-.............\\..".toCharArray();
		input[78] = "....|.....|...............................\\//.................|..........|.-.........\\................././....".toCharArray();
		input[79] = "../....-...............-...|...............-...-................./......-..-/...........-....../..........|-..".toCharArray();
		input[80] = "..........|.|/..............................-./|...|..|......\\............................-...-...|...........".toCharArray();
		input[81] = "..\\..........................................................-......................../../................-...".toCharArray();
		input[82] = ".....|\\..........................\\................|................\\.../.......\\..|.\\.........-.......\\.-.....".toCharArray();
		input[83] = ".......................|..\\..........|.......\\.-.................\\\\........-............|.....................".toCharArray();
		input[84] = "......./.................|.\\...|............................./\\............\\.....|.....|-...|.....|.......|...".toCharArray();
		input[85] = "....../...-.........../|...............-.............................\\../...........\\...........|...........|.".toCharArray();
		input[86] = "..-..................\\....\\/.........|.....\\......................-...........................................".toCharArray();
		input[87] = "....|.....-................................/.........-|...........\\....\\.-./....\\........................../..".toCharArray();
		input[88] = "....../........\\................|......\\.......|.................................\\............................".toCharArray();
		input[89] = "....-......./......./-.../.......\\...........|..............|................................|......|.........".toCharArray();
		input[90] = "-....................-.............................|......\\......|.........//...........\\........\\............".toCharArray();
		input[91] = "\\............./...../.....-.......-\\...........-...........\\.|.............-/.....|......|.../................".toCharArray();
		input[92] = ".......................|/\\..\\............|..|...................../.......-\\........|.....|....\\.....\\...-....".toCharArray();
		input[93] = ".......-............|........................../..\\.|.................\\...............\\............|...|...|..".toCharArray();
		input[94] = "......................../..-.......-.......-...-.............\\............-......--..\\..................|...\\.".toCharArray();
		input[95] = "...............|.|......|.....\\................-.........../.........\\............\\.............-.............".toCharArray();
		input[96] = "..\\..........................|./......-........|....-.............-.......-.....\\................/............".toCharArray();
		input[97] = "...../..................................................|......\\..-........|..|.....................\\.....-...".toCharArray();
		input[98] = ".....\\....|.........|.........|.................../.............\\......|.......|..........\\............./.../.".toCharArray();
		input[99] = "./.../............../.|....\\./..././...................\\...........|...........-......................\\....\\..".toCharArray();
		input[100] = ".../..............................-...............................\\.............../..........-.............../".toCharArray();
		input[101] = "|..\\-.........\\../...........|.\\..........-.........../..........|-...................................-.......".toCharArray();
		input[102] = "...........................|../.....\\.........................|.....\\........../..............................".toCharArray();
		input[103] = "..................-..........|.........|....|............../...-..../.............../-........\\\\.\\.......-....".toCharArray();
		input[104] = ".............---..../....................-....-......../....../.........\\..\\............................../...".toCharArray();
		input[105] = "|.....|.......................././...............|.../..|.\\............-.................../............./....".toCharArray();
		input[106] = "..-........\\............./../|..........................--....|..\\.........|/./................/...../........".toCharArray();
		input[107] = "..........\\|.....................................--............/....../............\\.|......|.......-|......\\.".toCharArray();
		input[108] = "..........|-........\\....|.....................\\................................-\\...|......../.........\\..|..".toCharArray();
		input[109] = "..\\\\.........\\..|../................|.........\\.....--...-...\\................/.............................\\.".toCharArray();

        return input;
    }
}
