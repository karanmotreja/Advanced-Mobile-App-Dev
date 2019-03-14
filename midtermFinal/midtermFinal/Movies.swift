//
//  Movies.swift
//  midtermFinal
//
//  Created by Karan Motreja on 3/14/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import Foundation

struct Movies: Decodable
{
    let name: String
    let url: String
}


class MovieDataModelController
{
    var allData = [Movies]()
    let fileName = "movies"
    
//    let datafilename = "data.plist"
//
//    func getDataFile(datafile: String) -> URL
//    {
//        let dirPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
//        let docDir = dirPath[0]
//        print(docDir)
//
//        return docDir.appendingPathComponent(datafile)
//
//    }
//
//    func writeData()
//    {
//        let dataFileURL = getDataFile(datafile: datafilename)
//        print(dataFileURL)
//
//        let plistencoder = PropertyListEncoder()
//        plistencoder.outputFormat = .xml
//        do{
//            let data = try plistencoder.encode()
//            try data.write(to: dataFileURL)
//        }
//        catch
//        {
//            print(error)
//        }
//    }
//
    
    
    func loadData(){
        if let pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist"){
            
            let plistdecoder = PropertyListDecoder()
            do {
                let data = try Data(contentsOf: pathURL)
                
                allData = try plistdecoder.decode([Movies].self, from: data)
            } catch {
                
                print(error)
            }
        }
    }
    
    func getMovies() -> [String]{
        var movies = [String]()
        for movie in allData{
            movies.append(movie.name)
        }
        return movies
    }
    
    func getURL(index:Int) -> String {
        return allData[index].url
    }
    
}
