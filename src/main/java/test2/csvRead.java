package com.test2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ¹¦ÄÜ:
 */
public class csvRead {
    private static Map<String,Integer> map = new HashMap<>();

    private static void updateMap (CSVRecord record){

                        String time1 = record.get(0);
                        String time2 = record.get(1);
                        String[] s1 = time1.split("/");
                        String[] s2 = time2.split("/");
                        String year1 = s1[0];
                        String month1 = s1[1];
                        int iyear1 = Integer.valueOf(year1);
                        //System.out.println(iyear1);
                        int imonth1 = Integer.valueOf(month1);
                        String year2 = s2[0];
                        String month2 = s2[1];
                        int iyear2 = Integer.parseInt(year2);
                        int imonth2 = Integer.parseInt(month2);
                        if (((iyear1-iyear2)==0 && (imonth1-imonth2)<=6)|| ((iyear1-iyear2)==1 && (12-imonth1+imonth2)<=6)){
                            if (map.containsKey(record.get(2))){
                                map.put(record.get(2),map.get(record.get(2))+1);
                            }else {
                                map.put(record.get(2),1);
                            }
                        }



    }

    public static void main(String[] args) {
        String basic = "D:\\360\\r\\idrevesion";
        File file = new File(basic);
        String[] resultList = file.list();

        for (String fileName : resultList) {
            if (fileName.contains("1")) {
                if (fileName.equals("batikidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 6915) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }



                } else if (fileName.equals("archivaidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1925) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("ariesidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1654) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("cayenneidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 13494) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("commons-fileuploadidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1347) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("commons-ioidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 4889) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("commons-netidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 339) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("curatoridReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 2185) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("datafuidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 40) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("deltaspikeidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1593) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("eagleidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1118) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("ecfidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 11681) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("eclipse-collectionsidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 3558) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("erlide_eclipseidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 29801) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("falconidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 7413) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("fineractidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 2095) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("freemarkeridReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 5150) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("gefidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 15235) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("goraidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 2675) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("hawkbitidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 19958) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("ivyidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 6159) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("kapuaidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 15027) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("knoxidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1868) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("kuraidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1359) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("lensidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 7240) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("leshanidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 6039) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("minaidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 4848) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("nutchidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 6006) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("oozieidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 2035) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("openjpaidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 6862) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("opennlpidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 2500) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("orcidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1281) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("rangeridReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1115) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("reddeeridReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1832) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("rolleridReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 6670) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("samzaidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 4480) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("stanbolidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 5339) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("stormidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 33633) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("strutsidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 5863) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("subclipseidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 956) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("tezidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 4410) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("unomiidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 470) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("vortoidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 4843) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("wicketidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 36856) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("wineryidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 1888) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }else if (fileName.equals("ws-wss4jidReversion1.csv")) {
                    try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                        for (CSVRecord record : records) {
                            if (record.getRecordNumber() == 9464) {
                                break;
                            } else {

                                if (record.getRecordNumber() == 1) {
                                    continue;
                                } else {
                                    updateMap(record);

                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }


            }








        }//suoyouwenjian
        Set<Map.Entry<String,Integer>> enSet = map.entrySet();
        for (Map.Entry<String,Integer> en : enSet){
            System.out.println(en.getKey());
        }
        for (Map.Entry<String,Integer> en : enSet){
            System.out.println(en.getValue());
        }





        }
    }
