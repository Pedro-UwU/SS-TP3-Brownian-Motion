

class Temperature:
    @staticmethod
    def getTemp( run , time):
        speeds = run['info']['v']
        temp = 0
        for idx , s in enumerate(speeds):
            temp+= 0.5 * run['mass'][idx] * s *s
        return temp
