//
//  Continents.swift
//  Lab2
//
//  Created by Karan Motreja on 2/19/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import Foundation

struct ContinentsDataModel : Codable
{
    var continent : String
    var countries : [String]
}

class ContinentsDataModelController
{
    var allData = [ContinentsDataModel]()
    let fileName = "continents2"
    let datafilename = "data.plist"
    
    
    func getDataFile(datafile: String) -> URL
    {
        let dirPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let docDir = dirPath[0]
        print(docDir)
        
        return docDir.appendingPathComponent(datafile)
    }
    
    func loadData()
    {
        let pathURL:URL?
        
        let dataFileURL = getDataFile(datafile: datafilename)
        
        if FileManager.default.fileExists(atPath: dataFileURL.path)
        {
            pathURL = dataFileURL
        }
        else
        {
            pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist")
        }
        
        let plistdecoder = PropertyListDecoder()
        do {
            let data = try Data(contentsOf: pathURL!)
            //decodes the property list
            allData = try plistdecoder.decode([ContinentsDataModel].self, from: data)
        }
        catch {
            // handle error
            print(error)
        }
    }
        
//        if let pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist"){
//            //creates a property list decoder object
//            let plistdecoder = PropertyListDecoder()
//            do {
//                let data = try Data(contentsOf: pathURL)
//                //decodes the property list
//                allData = try plistdecoder.decode([ContinentsDataModel].self, from: data)
//            }
//            catch {
//                // handle error
//                print(error)
//            }
//        }
//    }
    
    func getContinents() -> [String]
    {
        var continents = [String]()
        for item in allData
        {
            continents.append(item.continent)
        }
        return continents
    }
    
    func getCountries(index:Int) -> [String]
    {
        return allData[index].countries
    }
    
    func addCountry(index:Int, newCountry:String, newIndex: Int)
    {
        allData[index].countries.insert(newCountry, at: newIndex)
    }
    
    func deleteCountry(index:Int, countryIndex: Int)
    {
        allData[index].countries.remove(at: countryIndex)
    }

    func writeData()
    {
        let dataFileURL = getDataFile(datafile: datafilename)
        print(dataFileURL)
        
        let plistencoder = PropertyListEncoder()
        plistencoder.outputFormat = .xml
        do
        {
            let data = try plistencoder.encode(allData.self)
            try data.write(to: dataFileURL)
        }
        catch
        {
            print(error)
        }
        
    }
    

}
